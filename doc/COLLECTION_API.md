# API Lab Discussion

# Collections API Discussion

## Names and NetIDs

- Casey Szilagyi (crs79)
- Jin Cho (jc695)
- Kathleen Chen (kc387)
- Ji Yun Hyo (jh160)

### What is the purpose of each interface implemented by LinkedList?

- Serializable - Allows the user to store the data in byte form, makes the storage more generalized
- Cloneable - A class implements the Cloneable interface to indicate to the Object.clone() method
  that it is legal for that method to make a field-for-field copy of instances of that class.
- Iterable - Allows for iteration through the nodes of the linked list
- Collection - Allows for storage and access of data
- Deque - A linear collection that supports element insertion and removal at both ends.
- List - Gives the user control over the location of insertion/removal
- Queue - Allows for controlling of the order in which the nodes are added or removed (creates the '
  linked' structure)

### What is the purpose of each superclass of HashMap?

- AbstractMap - Provides the implementation details common to all different map types
- Object - A map is an object, and there are some methods that are common among all objects that are
  useful (such as toString)

### How many different implementations are there for a Set?

- AbstractSet, ConcurrentSkipListSet, CopyOnWriteArraySet, EnumSet, HashSet, JobStateReasons,
  LinkedHashSet, TreeSet
- 8 total implementations

### What methods are common to all collections?

- add
- addAll
- clear
- contains
- containsAll
- equals
- hashCode
- isEmpty
- iterator
- remove
- removeAll
- retainAll
- size
- toArray

### What methods are common to all Queues?

- add
- peek
- remove
- offer
- poll
- element
- addAll
- clear
- contains
- containsAll
- equals
- hashCode
- isEmpty
- iterator
- parallelStream
- removeAll
- removeIf
- retainAll
- size
- spliterator
- stream
- toArray

### What is the purpose of the collections utility classes?

- provides static methods that operate on or return collections
- operated on objects that are collection
- more narrow methods that work on specific types of collection to separate from the collection
  object abstract methods