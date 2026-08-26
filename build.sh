#!/usr/bin/env bash

set -euo pipefail

clean() {
  mvn clean
}

compile() {
  mvn compile
}

format() {
  mvn spotless:apply
}

test() {
  mvn test
}

jar() {
  mvn package
}

javadoc() {
  mvn javadoc:javadoc
}

deps() {
  mvn versions:display-dependency-updates
}

sver() {
  serialver -classpath target/classes $1
}

trace_test() {
  mvn -Ptrace test
}

native_test() {
  mvn -Pnative test
}

deploy() {
  mvn deploy
}


if [[ "$#" == "0" ]]; then
  clean
  format
  jar
  javadoc
  native_test
else
  for a in "$@"; do
    case "$a" in
    clean)
      clean
      ;;
    compile)
      compile
      ;;
    format)
      format
      ;;
    test)
      test
      ;;
    jar)
      jar
      ;;
    javadoc)
      javadoc
      ;;
    deps)
      deps
      ;;
    sver)
      sver $2
      ;;
    'trace-test')
      trace_test
      ;;
    'native-test')
      native_test
      ;;
    deploy)
      deploy
      ;;
    *)
      echo "Bad task: $a"
      exit 1
      ;;
    esac
  done
fi
