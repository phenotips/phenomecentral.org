/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

/**
 * This file can be easily modified for use with different parameters in different circumstances. However, since it is
 * being used in a single place (for now), it is left fairly hardcoded.
 * However, even in this state, it is fairly flexible.
 *
 * The bottom of the file contains the configurations that are run upon the inclusion of this file.
 */
var XWiki = (function(XWiki) {
    // Start XWiki augmentation
    var widgets = XWiki.widgets = XWiki.widgets || {};

    widgets.UniquenessValidator = Class.create({
        initialize : function(input, serviceUrl) {
            this.input = input;
            this.valid = true;
            this.state = 'NEW';
            this.request = 0;
            this.value = input.value;
            this.serviceUrl = serviceUrl;
            if (!this.input.__validation) {
                this.input.__validation = new LiveValidation(this.input, {validMessage: '', wait : 500});
            }
            this.input.__validation.add(this.validate.bind(this));
        },
        check : function() {
            if (this.input.value != this.value) {
                this.value = this.input.value;
                this.state = 'CHECKING';
                new Ajax.Request(this.serviceUrl, {
                    parameters : { outputSyntax: 'plain', eid: this.value, id: XWiki.Model.serialize(new XWiki.DocumentReference(XWiki.currentDocument.wiki, XWiki.currentDocument.space, XWiki.currentDocument.page))},
                    on200 : this.self.bindAsEventListener(this),
                    on403 : this.empty.bindAsEventListener(this),
                    on404 : this.available.bindAsEventListener(this),
                    on409 : this.exists.bindAsEventListener(this),
                    onComplete: this.responded.bindAsEventListener(this)
                });
            }
        },
        validate : function(value) {
            if (this.state == 'DONE') {
                this.value == value && (this.valid || Validate.fail("This identifier already exists"));
            }
            this.check();
            return true;
        },
        self : function() {
            this.valid = true;
        },
        available : function() {
            this.valid = true;
        },
        empty : function() {
            this.valid = false;
        },
        exists : function() {
            this.valid = false;
        },
        responded : function() {
            this.state = 'DONE';
            this.input.__validation.validate();
        }
    });

    var init = function(event) {
        var serviceUrl = new XWiki.Document('UsernameValidation', 'PhenomeCentral').getURL('get');
        ((event && event.memo.elements) || [$('body')]).each(function(element) {
            element.select('input.check-unique, .external_id input').each(function(input) {
                if (!input.__uniqueness_validator) {
                    input.__uniqueness_validator = new XWiki.widgets.UniquenessValidator(input, serviceUrl);
                }
            });
        });
        return true;
    };

    (XWiki.domIsLoaded && init()) || document.observe("xwiki:dom:loaded", init);
    document.observe('xwiki:dom:updated', init);

    // End XWiki augmentation.
    return XWiki;
}(XWiki || {}));
