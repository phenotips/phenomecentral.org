# ---------------------------------------------------------------------------
# See the NOTICE file distributed with this work for additional
# information regarding copyright ownership.
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU Affero General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU Affero General Public License for more details.
#
# You should have received a copy of the GNU Affero General Public License
# along with this program.  If not, see http://www.gnu.org/licenses/
# ---------------------------------------------------------------------------

mkdir -p /var/log/exomiser/ && python /var/lib/exomiser/scripts/exomiser_cron.py --since=`date --date="1 hour ago" +%Y-%m-%d` /var/lib/phenotips /var/lib/exomiser/exomiser-cli-7.2.3/exomiser-cli-7.2.3.jar /var/lib/exomiser/scripts/exomiser.credentials &> /var/log/exomiser/`date +%Y-%m-%d.%H%M%S`.log
