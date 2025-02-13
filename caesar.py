t='aditya'
en=""
print('encrypting... k=7')
for i in t:
    val=ord(i)
    val=val-97
    val=(val+7)%26
    val=val+97
    en=en+chr(val)
print(en)
print('decryption...')
de=""
for i in en:
    val=ord(i)
    val=val-97
    val=(val-7)
    if val<0:
        val=26-(-val)%26
    else:
        val=val%26
    de=de+chr(val+97)
print(de)


