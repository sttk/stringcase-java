#!/usr/bin/env bash

errcheck() {
  exitcd=$1
  if [[ "$exitcd" != "0" ]]; then
    exit $exitcd
  fi
}

clean() {
  mvn clean
  errcheck $?
}

compile() {
  mvn compile
  errcheck $?
}

test() {
  mvn test
  errcheck $?
}

jar() {
  mvn package
  errcheck $?
}

javadoc() {
  mvn javadoc:javadoc
  errcheck $?
}

deps() {
  mvn versions:display-dependency-updates
  errcheck $?
}

sver() {
  serialver -classpath target/classes $1
  errcheck $?
}

trace_test() {
  mvn -Ptrace test
  errcheck $?
}

native_test() {
  mvn -Pnative test
  errcheck $?
}

deploy() {
  mvn deploy
  errcheck $?
}


if [[ "$#" == "0" ]]; then
  clean
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

