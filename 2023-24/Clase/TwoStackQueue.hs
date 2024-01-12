module DataStructures.Queue.TwoStackQueue
  ( Queue
  , empty
  , isEmpty
  , enqueue
  , first
  , dequeue
  ) where

import qualified DataStructures.Stack.LinearStack as S

data Queue a = Q (S.Stack a) (S.Stack a)

empty :: Queue a
empty = Q S.empty S.empty

isEmpty :: Queue a -> Bool
isEmpty (Q s1 s2) = S.isEmpty s1

enqueue :: a -> Queue a -> Queue a
enqueue n (Q s1 s2)
    | S.isEmpty s1 = Q (S.push n s1) s2
    | otherwise    = Q s1 (S.push n s2)

first :: Queue a -> a
first (Q s1 s2)
      | S.isEmpty s1 = error "first on empty Queue"
      | otherwise = S.top s1

dequeue :: Queue a -> Queue a
dequeue (Q s1 s2)
      | S.isEmpty s1         = error "dequeue on empty Queue"
      | S.isEmpty (S.pop s1) = aux (S.empty) s2
      | otherwise            = Q (S.pop s1) s2 
        where
          aux v1 v2
            | S.isEmpty v2 = Q v1 v2 
            | otherwise    = aux (S.push (S.top v2) v1) (S.pop v2) 

mkQueue :: [a] -> Queue a
mkQueue lista = foldl (flip enqueue) empty lista