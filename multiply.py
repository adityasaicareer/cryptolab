txt="aditya"
en=''
for i in txt:

    val=ord(i)
    val=val-97
    val=(val*7)%26
    en=en+chr(val+97)

print(f"the encryption : {en}")

print("decryption....")

d=0
while (7*d)%26!=1:
    d+=1

de=''
for j in en:
    val=ord(j)
    val=val-97
    val=(val*d)%26
    de=de+chr(val+97)

print(f'decrypted message is {de}')