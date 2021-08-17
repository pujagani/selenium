#!/bin/bash
if [[ ! -f "/Users/Puja/Documents/GitHub/selenium/bazel-bin/java/test/org/openqa/selenium/environment/appserver_deploy.jar" ]]
then
  echo "Building test web server"
  bazel build //java/test/org/openqa/selenium/environment:appserver_deploy.jar
fi
