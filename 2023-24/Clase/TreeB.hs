module TreeB (TreeB,
              isEmpty) where

import qualified DataStructures.Queue.LinearQueue as Q

data TreeB a = Empty | Node a (TreeB a) (TreeB a) deriving (Show)

t1 = Node 1 ( Node 2 (Node 4 Empty Empty) 
                     (Node 5 Empty Empty))
            ( Node 3 (Node 6 Empty Empty) 
                     (Node 2 Empty Empty) )

isEmpty :: TreeB a -> Bool
isEmpty Empty = True
isEmpty t = False

-- Returns a path from root to the given element. 
-- If the element is not in the tree return an empty path
onePathTo :: (Eq a) => a -> TreeB a -> [a]
onePathTo _ Empty = []
onePathTo y (Node x lt rt) 
  | x == y = [x]
  | otherwise = if (null p) then [] else x:p
  where
    p' = onePathTo y lt
    p = if(null p') then (onePathTo y rt) else p'

-- Returns a list with all paths from root to the given element. 
-- If the element is not in the tree return an empty list of paths
pathsTo :: (Eq a) => a -> TreeB a -> [[a]]
pathsTo _ Empty = []
pathsTo y (Node x lt rt)
  | x == y = [x]:pp
  | otherwise = pp
    where 
      pp = [ x:p| p<-(pathsTo y lt)++(pathsTo y rt)]

preOrder :: TreeB a -> [a]
preOrder Empty = []
preOrder (Node x lt rt) = (x:preOrder lt) ++ preOrder rt 

inOrder :: TreeB a -> [a]
inOrder Empty = []
inOrder (Node x lt rt) = (inOrder lt)++(x:(inOrder rt))

postOrder:: TreeB a -> [a]
postOrder Empty =  []
postOrder (Node x lt rt) = (postOrder lt)++(postOrder rt)++[x]

-- Traversal by levels
breathFirst:: TreeB a -> [a]
breathFirst t = aux (Q.enqueue t Q.empty) []
    where
      aux q l
        | Q.isEmpty q         = l
        | isEmpty (Q.first q) = aux (Q.dequeue q) l
        | otherwise           = aux q' l'
        where
          (Node x lt rt) = Q.first q
          q' = Q.enqueue rt (Q.enqueue lt (Q.dequeue q))
          l' = l ++ [x]

isHop :: (Ord a) => TreeB a -> Bool
isHop Empty = True
isHop (Node x lt rt) = (esMenor x lt) && (esMenor x rt) && isHop lt && isHop rt
  where
    esMenor y Empty = True
    esMenor y (Node z _ _) = y <= z 