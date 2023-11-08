module Tree (Tree,
             sumT) 
             where

data Tree a = Empty | Node a  [Tree a]

t1 = Node 1 [ Node 2  [ Node 4 [ ] ,
                      Node 5 [ ] ,
                      Node 6 [ ] ] ,
             Node 3 [ Node 7 [ ] ] 
            ]

sumT :: (Num a) => Tree a -> a
sumT Empty = 0
sumT (Node x tt) = x + sum [sumT t | t <- tt]

-- Número de nodos en todo el árbol
size :: Tree a -> Int
size Empty = 0
size (Node _ tt) = foldr (\x y -> size x + y ) 1 tt

-- Número de nodos hoja
nLeaves :: Tree a -> Int
nLeaves Empty       = 0
nLeaves (Node _ []) = 1
nLeaves (Node _ tt) = foldr (\t y -> nLeaves t + y) 0 tt

allLeaves :: Tree a -> [a]
allLeaves Empty = []
allLeaves (Node a []) = [a]
allLeaves (Node x tt) = foldr (\x y -> allLeaves x ++ y) [] tt

-- Número de nodos internos
nInternals :: Tree a -> Int
nInternals Empty = 0
nInternals (Node _ []) = 0
nInternals (Node _ q) = foldr (\x y -> nInternals x + y) 1 q  

heightT :: Tree a  -> Int
heightT Empty = 0
heightT (Node _ []) = 1
heightT (Node _ tt) = 1 + maximum [heightT t | t <- tt]

-- Devuelve la lista de elementos en un nivel
atLevel :: Int -> Tree a -> [a]
atLevel n Empty= []
atLevel 0 (Node x tt) = [x]
atLevel n t@(Node x tt) = concat [atLevel (n-1) th | th <- tt]