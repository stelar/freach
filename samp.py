f=open("application.log", "r")
if f.mode == 'r':
    content=f.readlines()

print(len(content))

error=[]
for c in content:
    if "ERROR" in c:
        error.append(c.split("ERROR",1)[1])

print(error[1])

uniqError=[]

for e in error:
    e=e.replace(" ","")
    if e not in uniqError:
        uniqError.append(e)

uni=[]
for c in content:
    if "ERROR" in c:
        uni.append(c)

un=[]

for e in uni:
    e=e.replace(" ","")
    if e not in un:
        un.append(e)
print("Unique errors ",len(uniqError))
print("Unique Errors (considering errors are unique for each node",len(un))



