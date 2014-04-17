#
# add the "Hi-Res" key before the end of the plist file
#
sed -i '' '/\/dict/i \
<key>NSHighResolutionCapable</key> \
<true/>
' deploy/release/WikiReader.app/Contents/Info.plist 

