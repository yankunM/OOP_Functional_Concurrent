class Merger:

    @staticmethod
    def merge(tree1, tree2):
        result = []
        i, j = 0, 0
        # two iterators
        it1 = iter(tree1.root)
        it2 = iter(tree2.root)
        x = next(it1).val
        y = next(it2).val

        while i < tree1.size and j < tree2.size:
            try:
                if x < y:
                    result.append(x)
                    i += 1
                    x = next(it1).val
                else:
                    result.append(y)
                    j += 1
                    y = next(it2).val
            except:
                pass

        while i < tree1.size:
            try:
                result.append(x)
                i += 1
                x = next(it1).val
            except:
                pass

        while j < tree2.size:
            try:
                result.append(y)
                j += 1
                y = next(it2).val
            except:
                pass

        return result


class BinarySearchTree:

    def __init__(self, name, root):
        self.name = name
        self.root = root
        self.size = 0

    def add_all(self, *args):
        for i in args:
            self.root.insert(i)
            self.size += 1

    def __str__(self):
        return "[" + self.name + "] " + self.root.string()


class Node:
    def __init__(self, val=None):
        self.left = None
        self.right = None
        self.val = val

    def insert(self, val):
        if not self.val:
            self.val = val
            return

        if self.val == val:
            return

        if val < self.val:
            if self.left:
                self.left.insert(val)
                return
            self.left = Node(val)
            return

        if self.right:
            self.right.insert(val)
            return
        self.right = Node(val)

    def string(self):
        string = ""
        if self.val:
            string += str(self.val)
        if self.val == 0:
            string += str(0)
        if self.left:
            string += " L:(" + self.left.__str__() + ")"
        if self.right:
            string += " R:(" + self.right.__str__() + ")"
        return string

    def __str__(self):
        string = ""
        if self.val:
            string += str(self.val)
        if self.val == 0:
            string += str(0)
        return string

    def __iter__(self):
        def generator():
            if self.left:
                for leaf in self.left:
                    yield leaf
            yield self
            if self.right:
                for leaf in self.right:
                    yield leaf

        return generator()


# if __name__ == "__main__":
#     t1 = BinarySearchTree(name="Oak", root=Node())
#     t2 = BinarySearchTree(name="Birch", root=Node())
#     t1.add_all(5, 3, 9, 0)
#     t2.add_all(1, 0, 10, 2, 7)
#     print(t1)
#     for x in t1.root:
#         print(x)
#     for x in t2.root:
#         print(x)
#     print(Merger.merge(t1, t2))
