#!/bin/bash

set -e

echo "=== Java Debugger (JDB) Demo ==="
echo "This script demonstrates debugging the PowerCalculator application"
echo ""

# Check for Java and Gradle
command -v java >/dev/null 2>&1 || { echo "Java is not installed. Aborting."; exit 1; }
command -v gradle >/dev/null 2>&1 || { echo "Gradle is not installed. Aborting."; exit 1; }

# Compile with debug information
echo "1. Compiling with debug information..."
./gradlew compileJava

if [ ! -d "build/classes/java/main" ]; then
    echo "Compilation failed or output directory not found."
    exit 1
fi

echo ""
echo "2. To start JDB debugging, run:"
echo "   jdb -classpath build/classes/java/main org.example.Main [args...]"
echo ""
echo "3. Common JDB commands:"
echo "   - stop at org.example.PowerCalculatorGUI:403
   # set breakpoint at line 403"
echo "   - run                                      # start the program"
echo "   - step                                     # step into"
echo "   - next                                     # step over"
echo "   - cont                                     # continue execution"
echo "   - print x                                  # print variable value"
echo "   - locals                                   # show local variables"
echo "   - quit                                     # exit debugger"
echo ""
echo "4. Example debugging session:"
echo "   jdb -classpath build/classes/java/main org.example.PowerCalculator"
echo "   stop at org.example.PowerCalculatorGUI:403"
echo "   run"
echo "   print result"
echo "   step"
echo "   locals"
echo "   cont"
echo "   quit"
echo ""

# Optional: Launch JDB if requested
if [[ "$1" == "--run" ]]; then
    shift
    echo "Launching JDB..."
    jdb -classpath build/classes/java/main org.example.Main "$@"
fi

echo "=== End of JDB Demo ==="