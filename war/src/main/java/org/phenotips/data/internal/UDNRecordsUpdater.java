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

import org.phenotips.data.events.PatientChangingEvent;
import org.phenotips.data.permissions.Collaborator;
import org.phenotips.data.permissions.Owner;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import org.xwiki.component.annotation.Component;
import org.xwiki.context.Execution;
import org.xwiki.observation.AbstractEventListener;
import org.xwiki.observation.event.Event;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.xpn.xwiki.XWikiContext;
import com.xpn.xwiki.XWikiException;
import com.xpn.xwiki.doc.XWikiDocument;
import com.xpn.xwiki.objects.BaseObject;

/**
 * Add the UDN-I group as a collaborator with view rights if case is owned by users baylorudn, dukeudn, harvardudn, nihudn, stanfordudn, uclaudn, and vanderbiltudn.
 *
 * @version $Id$
 * @since 1.2
 */

@Component
@Named("udn-records-updater")
@Singleton
public class UDNRecordsUpdater extends AbstractEventListener
{
    private static final String UDNI_NAME = "xwiki:Groups.UDN-I";

    private static final String VIEW_ACCESS_KEY = "view";

    private static final String COLLABORATOR_KEY = "collaborator";

    private static final String ACCESS_PROPERTY_NAME = "access";

    private static final String OWNER_KEY = "owner";

    private static final List<String> UDN_SITE_ACCOUNTS = Arrays.asList("xwiki:XWiki.baylorudn", "xwiki:XWiki.dukeudn",
            "xwiki:XWiki.harvardudn", "xwiki:XWiki.nihudn", "xwiki:XWiki.stanfordudn", "xwiki:XWiki.uclaudn",
            "xwiki:XWiki.vanderbiltudn");

    @Inject
    private Execution execution;

    @Inject
    private Logger logger;

    /**
     * Default constructor, sets up the listener name and the list of events to subscribe to.
     */
    public UDNRecordsUpdater()
    {
        super("udn-records-updater", new PatientChangingEvent());
    }

    @Override
    public void onEvent(Event event, Object source, Object data)
    {
        XWikiDocument doc = (XWikiDocument) source;
        XWikiContext context = (XWikiContext) this.execution.getContext().getProperty("xwikicontext");

        try {
            // check if owner of the record is in UDN site accounts
            BaseObject owner = doc.getXObject(Owner.CLASS_REFERENCE);
            if (owner == null || !UDN_SITE_ACCOUNTS.contains(owner.getStringValue(OWNER_KEY))) {
                return;
            }

            // check if UDN-I group is already assigned as a collaborator 
            List<BaseObject> collaborators = doc.getXObjects(Collaborator.CLASS_REFERENCE);
            if (collaborators != null && !collaborators.isEmpty()) {
                for (BaseObject collaborator : collaborators) {
                    if (collaborator == null) {
                        continue;
                    }

                    String collaboratorName = collaborator.getStringValue(COLLABORATOR_KEY);
                    if (!StringUtils.isBlank(collaboratorName) && collaboratorName.equals(UDNI_NAME)) {
                        return;
                    }
                }
            }

            // add the UDN-I group as a collaborator with view rights
            BaseObject collaboratorObject = doc.newXObject(Collaborator.CLASS_REFERENCE, context);
            collaboratorObject.setStringValue(COLLABORATOR_KEY, UDNI_NAME);
            collaboratorObject.setStringValue(ACCESS_PROPERTY_NAME, VIEW_ACCESS_KEY);
        } catch (XWikiException ex) {
            this.logger.error("Failed to set UDN-I group as a collaborator with view rights for entity [{}]: {}",
                    doc.getDocumentReference(), ex.getMessage(), ex);
        }
    }
}
