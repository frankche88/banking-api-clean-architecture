#!/bin/bash
set -e

# ENSURE REQUIRED VARS ARE SET:
if [ -z ${CF_MANIFEST+x} ]; then
  echo "Please set CF_MANIFEST with the filename of the manifest to use for the deploy."
  exit 1
fi

if [ -z ${CF_APP_NAME+x} ]; then
  echo "Please set CF_APP_NAME with the name of the app to be deployed."
  exit 1
fi

# DEPLOY APP WITH TEMPORARY NAME '_NEW':
#   Note that traffic is load balanced between any existing app and the new app,
#   by virtue of them having the same route mapped at this point, and CF's
#   distribution over mappings of the same kind:
./cf push ${CF_APP_NAME} -f ${CF_MANIFEST}

# CAN RUN SMOKE TEST AGAINST _NEW APP BEFORE PROCEEDING
# (can map a new route to the _new app and reference this in smoke test configs)

# CLEANUP EXISTING APP IF NECESSARY:
#if ./cf app ${CF_APP_NAME} ; then
#  ./cf stop ${CF_APP_NAME}
#  ./cf delete ${CF_APP_NAME} -f
#fi

# RENAME NEWLY-DEPLOYED APP PROPERLY:
#./cf rename ${CF_APP_NAME}_new ${CF_APP_NAME}

# CHECK STATUS OF APP:
#./cf apps | grep ${CF_APP_NAME}
