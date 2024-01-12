-------------------------------------------------------------------------------
-- Linear implementation of Team Queues
--
-- Data Structures. Grado en Informática. UMA.
-------------------------------------------------------------------------------

module DataStructures.TeamQueue.LinearTeamQueue
  ( TQueue
-- interfaz basica
  , empty        -- 0.25
  , isEmpty      -- 0.25
  , first        -- 0.25
  , dequeue      -- 0.25
  , enqueue      -- 1.00
-- extras
  , mkTQ         -- 1.00 con plegado, 0.75 en otro caso
  , filterTQ     -- 1.00
  , foldrTQ      -- 1.00
  , firstTeam    -- 1.00 recursiva, 0.75 en otro caso
  , dequeueTeam  -- 1.00 recursiva, 0.75 en otro caso
  , getTeam      -- 1.00 recursiva, 0.75 en otro caso
  , toList       -- 1.00 con plegado, 0.75 en otro caso
  ) where

import Data.List(intercalate)



data TQueue = Empty | Node Integer TQueue
type Equipos = Integer

-- empty. Crea una cola de equipos vacia.
-- 0.25
empty ::TQueue
empty = Empty

-- isEmpty. Comprueba si una cola de equipos esta vacía.
-- 0.25
isEmpty ::TQueue -> Bool
isEmpty Empty = True
isEmpty _     = False

-- first. Devuelve el primer elemento de una cola de equipos.
-- Si la cola está vacía lanza un error
-- 0.25
first :: TQueue -> Integer
first Empty      = error "Cola vacia"
first (Node x y) = x

-- dequeue. Desencola el primer elemento de una cola de equipos.
-- Si la cola está vacía lanza un error.
-- 0.25
dequeue :: TQueue -> TQueue
dequeue Empty      = error "Cola vacia"
dequeue (Node x y) = y
-- enqueue. Encola un elemento en una cola de n equipos, numerados del 0 a n-1
-- Si el elemento es el primero de su equipo, se añade la final de la cola.
-- En otro caso, se inserta después del primer elemento de su equipo.
-- 1.00
enqueue ::Integer -> Equipos -> TQueue -> TQueue
enqueue n e Empty = undefined
enqueue n e (Node x y)= undefined


-- mkTQ. Crea una cola de n equipos con los elementos de la lista.
-- 1.00 con plegado, 0.75 en otro caso
mkTQ :: Equipos -> [Integer] -> TQueue
mkTQ e [] = undefined
mkTQ e x =  undefined


-- foldrTQ. Realiza un plegado por la derecha de la cola de equipos
-- usando la funcion y el caso base dados.
-- 1.00
foldrTQ :: (Integer -> b -> b) -> b -> TQueue -> b
foldrTQ f b Empty = undefined
foldrTQ f b (Node x y) =  undefined

-- filterTQ. Crea una cola de equipos con los elementos de la cola
-- de equipos dada que cumplen el predicado.
-- 1.00
filterTQ :: (Integer -> Bool) -> TQueue -> TQueue
filterTQ f Empty = undefined
filterTQ f (Node x y) = undefined

-- firstTeam. Devuelve una lista con el primer equipo de la cola
-- 1.00 Implementación recursiva, 0.75 en otro caso
firstTeam ::Equipos -> TQueue -> [Integer]
firstTeam e Empty = undefined
firstTeam e (Node x y) = undefined
-- dequeueTeam. Desencola el primer equipo de la cola de equipos
-- 1.00 Implementación recursiva, 0.75 en otro caso
dequeueTeam :: Equipos -> TQueue -> TQueue
dequeueTeam e Empty = undefined
dequeueTeam e (Node x y) = undefined


-- getTeam. Devuelve una lista con los elementos de un equipo t que están en la cola de n equipos
-- El primer argumento es el equipo que se busca (t) y el segundo el número de equipos de la cola (n)
-- 1.00 Implementación recursiva, 0.75 en otro caso
getTeam :: Equipos -> Equipos -> TQueue -> [Integer]
getTeam z e Empty = undefined
getTeam z e (Node x y)= undefined


-- toList. Devuelve una lista con todos los elementos de la cola de equipos
-- 1.00 con plegado. 0.75 en otro caso

toList :: TQueue -> [Integer]
toList Empty = undefined
toList (Node x y) = undefined 


{-
--quitar estos comentarios para probar

-- ===============
-- Ejemplos de uso
-- ===============

s1 = empty
LinearTQueue()

s2 = isEmpty s1
True

s3 = enqueue 11 3 (enqueue 15 3 (enqueue 0 3 s1))
-- LinearTQueue(0,15,11)

s4 = dequeue s3
-- LinearTQueue(15,11)

s5 = mkTQ 3 [1..10]
-- LinearTQueue(1,10,7,4,2,8,5,3,9,6)

s6 = filterTQ even s5
-- LinearTQueue(10,4,2,8,6)

s7 = foldrTQ (+) 0 s6
-- 30

s8 = firstTeam 3 s6
-- [10,4]

s9 = dequeueTeam 3 s6
-- LinearTQueue(2,8,6)

s10 = getTeam 0 3 s6
-- [6]

s11 = toList s6
-- [10,4,2,8,6]

-}










-- ===============================================
--   NO TOCAR ESTA PARTE
-- ===============================================

-- Showing a team TQueue
instance Show TQueue where
  show q = "LinearTQueue(" ++ intercalate "," (aux q) ++ ")"
    where
     aux Empty      =  []
     aux (Node x q) =  show x : aux q

-- Team Queue equality
instance Eq TQueue where
  Empty      == Empty           =  True
  (Node x q) == (Node x' q')    =  x==x' && q==q'
  _          == _               =  False
