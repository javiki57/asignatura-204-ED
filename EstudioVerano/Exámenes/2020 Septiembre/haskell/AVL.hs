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
emptyBin x = B x []

remainingCapacity :: Bin -> Capacity
remainingCapacity (B c w) = c - sum w


addObject :: Weight -> Bin -> Bin
addObject o (B c []) = B (c - o) [o]
addObject o (B c (x:xs))
    | remainingCapacity (B c (o:x:xs)) < 0 = error "El objeto no cabe en el cubo"
    | otherwise = B (c - o) (o:x:xs)


maxRemainingCapacity :: AVL -> Capacity
maxRemainingCapacity Empty = 0
maxRemainingCapacity (Node _ _ maxCap leftChild rightChild) = max maxCap (max (maxRemainingCapacity leftChild) (maxRemainingCapacity rightChild))


height :: AVL -> Int
height Empty = 0
height (Node _ h _ _ _) = h


nodeWithHeight :: Bin -> Int -> AVL -> AVL -> AVL
nodeWithHeight bin h leftChild rightChild =
    let maxCap = max (remainingCapacity bin) (maxRemainingCapacity leftChild `max` maxRemainingCapacity rightChild)
    in Node bin h maxCap leftChild rightChild


node :: Bin -> AVL -> AVL -> AVL
node bin l r = nodeWithHeight bin altura l r
    where
      altura = 1 + max (height l) (height r)


rotateLeft :: Bin -> AVL -> AVL -> AVL
rotateLeft c l (Node r _ _ r1 r2) = node r (node c l r1) r2


addNewBin :: Bin -> AVL -> AVL
addNewBin b Empty = Node b 1 (remainingCapacity b) Empty Empty
addNewBin b (Node bn h c lt Empty) = node bn lt (Node b 1 (remainingCapacity b) Empty Empty)
addNewBin b (Node bn h c Empty rt) = rotateLeft bn Empty (addNewBin b rt)
addNewBin b (Node bn h c lt rt) = node bn lt (addNewBin b rt)
 

addFirst :: Capacity -> Weight -> AVL -> AVL
addFirst c w Empty = addNewBin (B (c - w) [w]) Empty
addFirst c w t@(Node bn hn cn lt rt)
        | maxRemainingCapacity t < w = addNewBin (B (c - w) [w]) t
        | maxRemainingCapacity lt >= w = addFirst c w lt
        | remainingCapacity bn >= w = node (addObject w bn) lt rt
        | otherwise = addFirst c w rt


addAll:: Capacity -> [Weight] -> AVL
addAll c ws = aux c ws Empty
  where
    aux c [] t = t
    aux c (w : ws) t = aux c ws (addFirst c w t)


toList :: AVL -> [Bin]
toList Empty = []
toList (Node b h c l r) = toList l ++ [b] ++ toList r

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