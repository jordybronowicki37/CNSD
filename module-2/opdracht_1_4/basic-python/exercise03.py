numbers = [4, 20, 10, 18, 9, 17, 11, 15]
strings = ["18", "15", "5", "6", "10", "16", "11", "8"]

result = []

# Add your code here.
for num in numbers:
    if num % 3 == 0:
        result.append(num)

result = result * 3

for s in strings:
    if len(s) == 2:
        n = int(s)
        if n == 16:
            break
        result.append(n)

print(result)
