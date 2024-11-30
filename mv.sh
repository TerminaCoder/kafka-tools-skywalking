export JAVA_11_HOME="/Users/lxf/Library/Java/JavaVirtualMachines/corretto-11.0.23/Contents/Home"
### 当前终端使用JAVA_11_HOME
export JAVA_HOME=${JAVA_11_HOME}
mvn package -DskipTests=true
cp ./target/kafka-tools-skywalking-1.0-SNAPSHOT.jar /Applications/Offset\ Explorer\ 3.app/Contents/Resources/app/plugins
