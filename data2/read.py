import xml.etree.ElementTree as ET
tree = ET.parse('ers-std.xml.new')
root = tree.getroot()

for customer in root:
    print customer.tag 
    for channel in customer.iter("channel"):
        for feature in channel.iter("feature"):
            for tag in feature.iter("tag"):
                print tag.text,
