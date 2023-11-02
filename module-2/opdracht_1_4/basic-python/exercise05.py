# Replace the function definitions by the correct implementation.
class Rectangle:
    def __init__(self, x, y):
        self.x = x
        self.y = y

    def area(self):
        return self.x * self.y

    def describe(self):
        return f'This {self.x}x{self.y} rectangle has an area of {self.area()}'


first = Rectangle(3, 5)
second = Rectangle(2, 1)
third = Rectangle(4, 3)

for r in [first, second, third]:
    print(r.describe())
