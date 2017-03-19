#!/bin/bash

rm -rf gen
rm -rf bin
rm -rf bin2
if [ -f classes2.dex ];then
	rm classes2.dex
fi 

mkdir gen
mkdir bin

aapt package -f -m -J ./gen -S res -M AndroidManifest.xml -I /Users/tong/Applications/android-sdk-macosx/platforms/android-22/android.jar
/Library/Java/JavaVirtualMachines/jdk1.7.0_79.jdk/Contents/Home/bin/javac -bootclasspath /Users/tong/Applications/android-sdk-macosx/platforms/android-22/android.jar -cp libs/android-support-multidex.jar -d bin src/com/example/hellodemo/*.java gen/com/example/hellodemo/R.java

mkdir -p bin2/com/example/hellodemo/
cp  bin/com/example/hellodemo/UserInfo.class bin2/com/example/hellodemo/
rm bin/com/example/hellodemo/UserInfo.class

cp -r android-support-multidex/android bin/
cp -r android-support-multidex/META-INF bin

cd bin 

jar cvf classes.jar *
cd ..
dx --dex --output=bin/classes.dex bin/classes.jar

cd bin2

jar cvf classes2.jar *
cd ..
dx --dex --output=bin/classes2.dex bin2/classes2.jar

aapt package -f -M AndroidManifest.xml -S res -I /Users/tong/Applications/android-sdk-macosx/platforms/android-22/android.jar -F bin/resources.ap_  

/Library/Java/JavaVirtualMachines/jdk1.7.0_79.jdk/Contents/Home/bin/java -cp /Users/tong/Applications/android-sdk-macosx/tools/lib/sdklib.jar com.android.sdklib.build.ApkBuilderMain hello.apk -v -u -z bin/resources.ap_ -f bin/classes.dex -rf src

pwd
cp bin/classes2.dex classes2.dex
aapt add -f hello.apk classes2.dex
 cd Auto-sign
 /Library/Java/JavaVirtualMachines/jdk1.7.0_79.jdk/Contents/Home/bin/java -jar signapk.jar testkey.x509.pem testkey.pk8 ../hello.apk ../hellosign1.apk



rm -rf gen
rm -rf bin
rm -rf bin2
rm -rf out
if [ -f classes2.dex ];then
	rm classes2.dex
fi