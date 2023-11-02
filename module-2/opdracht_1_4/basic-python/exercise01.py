# Change this code.
first = 16.1
second = ""
first_array = [1, 2, 3]
second_array = [1, 2]

if first > 15:
    print('1')

if isinstance(first, float):
    print('2')

if first_array:
    print('3')

if len(second_array) == 2:
    print('4')

if len(first_array) + len(second_array) == 5:
    print('5')

if first_array and first_array[0] == 1:
    print('6')

if not second:
    print('7')

if isinstance(second, str):
    print('8')
