Problem source: [Google Problem](https://medium.com/@alexgolec/google-interview-problems-ratio-finder-d7aa8bf201e3)

Statement: Given a conversion table in the form of csv, convert from one type to another.

Example:

a = b<br/>
d = 3a<br/>
c = 3b<br/>

Convert 3c to d

a = b<br/>
i.e. b = a<br/>
d = 3a<br/>
d = 3b<br/>
b = c/3<br/>
d = 3*c/3<br/>
d = c<br/>
c = d<br/>

Ans: 3c = 3d
<br/>
Output of sample run: <br/>
<br/>
 Number of rates in conversion table: <br/>
 3 <br/>
 Enter conversion rates: <br/>
 a,b,1 <br/>
 a,d,3 <br/>
 b,c,3 <br/>
 Convert from :c<br/>
 Convert to :d<br/>
 Value :3<br/>
 Converted value: 3.0

Algorithm:

1. Build HashMap of Origin as <br>
   srcType -> [ destnType -> val ] <br>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;        -> [ destnType2 -> val ] <br>
   srcType2 -> [ destnType -> val ] <br>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;        -> [ destnType2 -> val ] <br>
   
2. From above map build adjacency list, that converts from one type to all other types, as follows:
    1. For each map entry in Origin, traverse to its sub types using above inner Map. Set path to starting type.
    2. traverse(path) <br>
    Now recursively do a bfs from recent visited node to all its connected nodes.
    Update adjacency matrix with each pairing of recent node to all previous nodes.
    
3. Use adjacency matrix to convert from one type to another