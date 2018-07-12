#!/bin/bash
set -e

echo "============== INSTALLING CLOUD FOUNDRY CLI CLIENT =============="
# https://github.com/cloudfoundry/cli/releases
wget --max-redirect=1 --output-document=cf_cli_6.26.0.tgz "https://cli.run.pivotal.io/stable?release=linux64-binary&version=6.26.0&source=github-rel"
gunzip cf_cli_6.26.0.tgz
tar -xvf cf_cli_6.26.0.tar

echo "============== LOGGING INTO CLOUD FOUNDRY =============="
./cf login -a=$CF_API -s=$CF_SPACE -o=$CF_ORGANIZATION -u=$CF_USERNAME -p=$CF_PASSWORD


echo "============== DEPLOYING ${CF_APP_NAME} TO ${CF_SPACE} SPACE ON CLOUD FOUNDRY =============="
ci/zero_downtime.sh
