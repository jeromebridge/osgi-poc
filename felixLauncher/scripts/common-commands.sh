# Can be piped to and will split the first line only based on the delimeter and index of the split string
# Param 1: Delimeter
# Param 2: Index To Get
split_and_get = { var1 = tac --list; var1 = $var1 get 0; var1 = $var1 split $1; ($var1) | var1 = tac --list; var1 = $var1 get $2; $var1 }

# Param 1: Bundle URL
ias = { install $1 | bundleNumber = split_and_get " " 2; start $bundleNumber }


# Equinox Version (Virgo)
# ias = { install -start $1 }

# Karaf
# ias = { install -s $1 }