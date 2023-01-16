{------------------------------------------------------------------------------
 - Student's name:
 -
 - Student's group:
 -----------------------------------------------------------------------------}

module AVL 
  ( 
    Weight
  , Capacity
  , AVL (..)
  , Bin
  , emptyBin
  , remainingCapacity
  , addObject
  , maxRemainingCapacity
  , height
  , nodeWithHeight
  , node
  , rotateLeft
  , addNewBin
  , addFirst
  , addAll
  , toList
  , linearBinPacking
  , seqToList
  , addAllFold
  ) where

type Capacity = Int
type Weight= Int

data Bin = B Capacity [Weight] 

data AVL = Empty | Node Bin Int Capacity AVL AVL deriving Show


emptyBin :: Capacity -> Bin
emptyBin capacity = B capacity []

remainingCapacity :: Bin -> Capacity
remainingCapacity (B cap xs) = cap

addObject :: Weight -> Bin -> Bin
addObject w (B cap xs)
            | w > cap   = error "El objeto no cabe en el cubo"
            | otherwise = (B (cap-w) (w:xs))

maxRemainingCapacity :: AVL -> Capacity
maxRemainingCapacity Empty = error "Empty tree."
maxRemainingCapacity (Node bin h cap izq der) = cap

height :: AVL -> Int
height Empty = error "Empty tree."
height (Node bin h cap izq der) = h


 
nodeWithHeight :: Bin -> Int -> AVL -> AVL -> AVL
nodeWithHeight (B cap xs) h left right = Node (B cap xs) h (maximum [cap - sum xs, maxRemainingCapacity left, maxRemainingCapacity right]) left right


node :: Bin -> AVL -> AVL -> AVL
node (B cap xs) izq der = (Node (B cap xs) (1 + max (height izq) (height der)) (maximum [cap - sum xs, maxRemainingCapacity izq, maxRemainingCapacity der]) izq der)

rotateLeft :: Bin -> AVL -> AVL -> AVL
rotateLeft c ele (Node b h cap rl rr) = node b rl (node c ele rr)

addNewBin :: Bin -> AVL -> AVL
addNewBin b Empty = Node b 1 (remainingCapacity b) Empty Empty
addNewBin b (Node bin h cap left right)
    | height left > height right + 1 = rotateLeft bin left (addNewBin b right)
    | otherwise = node bin left (addNewBin b right)
 
addFirst :: Capacity -> Weight -> AVL -> AVL
addFirst _ _ Empty = Empty
addFirst _ w (Node (B cap xs) h maxCap left right)
    | w > cap = addNewBin (emptyBin w) (Node (B cap xs) h maxCap left right)
    | maxCap >= w = node (addObject w (B cap xs)) left right
    | maxRemainingCapacity left >= w = node (B cap xs) (addFirst cap w left) right
    | otherwise = node (B cap xs) left (addFirst cap w right)

addAll:: Capacity -> [Weight] -> AVL
addAll _ [] = Empty
addAll w (x:xs) = addFirst w x (addAll w xs)

toList :: AVL -> [Bin]
toList Empty = []
toList (Node bin _ _ left right) = toList left ++ [bin] ++ toList right

{-
	SOLO PARA ALUMNOS SIN EVALUACION CONTINUA
  ONLY FOR STUDENTS WITHOUT CONTINUOUS ASSESSMENT
 -}

data Sequence = SEmpty | SNode Bin Sequence deriving Show  

linearBinPacking:: Capacity -> [Weight] -> Sequence
linearBinPacking _ _ = undefined

seqToList:: Sequence -> [Bin]
seqToList _ = undefined

addAllFold:: [Weight] -> Capacity -> AVL 
addAllFold _ _ = undefined



{- No modificar. Do not edit -}

objects :: Bin -> [Weight]
objects (B _ os) = reverse os

  
instance Show Bin where
  show b@(B c os) = "Bin("++show c++","++show (objects b)++")"