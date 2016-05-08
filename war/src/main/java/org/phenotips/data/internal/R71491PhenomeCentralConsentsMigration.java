/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/
 */
package org.phenotips.data.internal;

import org.phenotips.Constants;

import org.xwiki.component.annotation.Component;
import org.xwiki.model.EntityType;
import org.xwiki.model.reference.DocumentReferenceResolver;
import org.xwiki.model.reference.EntityReference;
import org.xwiki.model.reference.EntityReferenceSerializer;
import org.xwiki.model.reference.ObjectPropertyReference;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;

import com.xpn.xwiki.XWiki;
import com.xpn.xwiki.XWikiContext;
import com.xpn.xwiki.XWikiException;
import com.xpn.xwiki.doc.XWikiDocument;
import com.xpn.xwiki.objects.BaseObject;
import com.xpn.xwiki.objects.BaseProperty;
import com.xpn.xwiki.objects.IntegerProperty;
import com.xpn.xwiki.store.XWikiHibernateBaseStore.HibernateCallback;
import com.xpn.xwiki.store.XWikiHibernateStore;
import com.xpn.xwiki.store.migration.DataMigrationException;
import com.xpn.xwiki.store.migration.XWikiDBVersion;
import com.xpn.xwiki.store.migration.hibernate.AbstractHibernateDataMigration;

/**
 * Migrate early PhenomeCentral consents from individual properties to the aggregated {@code granted} property.
 *
 * @version $Id$
 * @since 1.0RC1
 */
@Component
@Named("R71491PhenomeCentralConsents")
@Singleton
public class R71491PhenomeCentralConsentsMigration extends AbstractHibernateDataMigration
    implements HibernateCallback<Object>
{
    private static final EntityReference CLASS_REFERENCE =
        new EntityReference("PatientConsent", EntityType.DOCUMENT, Constants.CODE_SPACE_REFERENCE);

    private static final String NEW_PROPERTY_NAME = "granted";

    /** Logging helper object. */
    @Inject
    private Logger logger;

    /** Resolves unprefixed document names to the current wiki. */
    @Inject
    @Named("current")
    private DocumentReferenceResolver<String> resolver;

    /** Serializes the class name without the wiki prefix, to be used in the database query. */
    @Inject
    @Named("compactwiki")
    private EntityReferenceSerializer<String> serializer;

    @Override
    public String getDescription()
    {
        return "Migrate early PhenomeCentral consents from individual properties to the aggregated 'granted' property";
    }

    @Override
    public XWikiDBVersion getVersion()
    {
        return new XWikiDBVersion(71491);
    }

    @Override
    public void hibernateMigrate() throws DataMigrationException, XWikiException
    {
        getStore().executeWrite(getXWikiContext(), this);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object doInHibernate(Session session) throws HibernateException, XWikiException
    {
        XWikiContext context = getXWikiContext();
        XWiki xwiki = context.getWiki();

        Query q = session.createQuery("select distinct o.name from BaseObject o where o.className = '"
            + this.serializer.serialize(CLASS_REFERENCE) + "'");

        List<String> docs = q.list();
        R71491PhenomeCentralConsentsMigration.this.logger.debug("Found {} documents with consents", docs.size());
        for (String docName : docs) {
            try {
                XWikiDocument doc = xwiki.getDocument(this.resolver.resolve(docName), context);
                BaseObject consents = doc.getXObject(CLASS_REFERENCE);
                List<String> granted = new LinkedList<>();

                Collection<BaseProperty<ObjectPropertyReference>> props = consents.getFieldList();
                List<String> toRemove = new LinkedList<>();
                for (BaseProperty<ObjectPropertyReference> oldProperty : props) {
                    if (Integer.valueOf(1).equals(oldProperty.getValue())) {
                        granted.add(oldProperty.getName());
                    }
                    if (oldProperty instanceof IntegerProperty) {
                        toRemove.add(oldProperty.getName());
                    }
                }
                for (String oldProperty : toRemove) {
                    consents.removeField(oldProperty);
                }
                consents.setDBStringListValue(NEW_PROPERTY_NAME, granted);
                doc.setComment("Migrated consents");
                doc.setMinorEdit(true);
                // There's a bug in XWiki which prevents saving an object in the same session that it was loaded,
                // so we must clear the session cache first.
                session.clear();
                ((XWikiHibernateStore) getStore()).saveXWikiDoc(doc, context, false);
                session.flush();
            } catch (Exception ex) {
                this.logger.warn("Failed to migrate consents for [{}]: {}", docName, ex.getMessage());
            }
        }
        return null;
    }
}
