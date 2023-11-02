balances = {
    'John': 53.2,
    'Jack': 93.83,
    'Jill': 14
}

# Add code to add Jake and pop Jill here.
balances["Jake"] = 31.4
jill = balances.pop("Jill")

# Change the format strings to get the expected output.
for name, balance in balances.items():
    print(f'Hello %s, your balance is %.2f' % (name, balance))
print(f'Hello Jill, your balance was {jill:.2f}')
