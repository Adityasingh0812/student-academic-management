#!/usr/bin/env bash
MVN_BIN="/Applications/IntelliJ IDEA.app/Contents/plugins/maven-plugin/lib/maven3/bin/mvn"
if command -v mvn &> /dev/null; then
    mvn spring-boot:run
elif [ -f "$MVN_BIN" ]; then
    "$MVN_BIN" spring-boot:run
else
    echo "Maven binary not found."
    exit 1
fi
