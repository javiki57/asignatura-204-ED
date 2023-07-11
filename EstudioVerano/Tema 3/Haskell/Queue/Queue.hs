------------------------------------------------------------
-- Estructuras de Datos
-- Tema 3. Estructuras de Datos Lineales
-- Pablo López
--
-- Módulo del TAD Cola
------------------------------------------------------------

------------------------------------------------------------
-- Implementación del TAD Cola
------------------------------------------------------------

module Queue(Queue,   -- lista de exportación: todo lo que aparece aquí es público
             isEmpty,
             empty,
             enqueue,
             first,
             dequeue
             ) where

import           Test.QuickCheck

{-
   Considera la siguiente cola:

                            cola con 3 elementos
                              /
                   primero   /  último
                        \ ----- /
                        7 1 5 2
                        -------
                           \
                           cola con 4 elementos

   Como las listas y las pilas, la cola es una estructura de
   datos recursiva: una cola de `n` elementos consta del primero
   seguido de una cola de `n-1` elementos.
   La cola más pequeña que puede construirse es la cola vacía.

   Por lo tanto, una cola puede ser:

      - la cola vacía (caso base)
      - el primero seguido de una cola (caso recursivo)

  Podemos representar las colas en Haskell mediante un tipo algebraico
  parametrizado y recursivo `Queue a`. Necesitaremos dos constructores
  de datos para:

      - la cola vacía (caso base)
      - la cola formada por el primero seguida del resto de la cola (caso recursivo)
-}

-- El tipo algebraico `Queue a` es la representación física de la cola.
-- Esta representación está **oculta** para que el tipo `Queue a` sea un TAD.

data Queue a = Empty
            | Node a (Queue a)
             deriving (Eq, Show)

-- Ejercicio: representa una cola `customers` en la que aparezcan del
-- primero al último los elementos "peter", "mary" y "john".

customers :: Queue String
customers = Node "peter" $ Node "mary" $ Node "john" Empty

-- Ejercicio: ¿Cómo construirías la cola `customers` como cliente del TAD?

-- Complejidad: O(?)
-- |
-- >>> empty
-- Empty
empty :: Queue a
empty = Empty

-- Complejidad: O(?)
-- |
-- >>> isEmpty empty
-- True
-- >>> isEmpty customers
-- False
isEmpty :: Queue a -> Bool
isEmpty Empty = True
isEmpty _     = False

-- Complejidad: O(?)
-- |
-- >>> first customers
-- "peter"
first :: Queue a -> a
first Empty      = error "Cola vacía"
first (Node a n) = a

-- Complejidad: O(?)
-- |
-- >>> dequeue customers
-- Node "mary" (Node "john" Empty)
--
-- >>> dequeue (dequeue customers)
-- Node "john" Empty
dequeue :: Queue a -> Queue a
dequeue Empty      = error "Cola vacía"
dequeue (Node a n) = n

-- Complejidad: O(?)
-- |
-- >>> enqueue "frank" customers
-- Node "peter" (Node "mary" (Node "john" (Node "frank" Empty)))
--
-- >>> enqueue "nicole" (enqueue "frank" customers)
-- Node "peter" (Node "mary" (Node "john" (Node "frank" (Node "nicole" Empty))))
enqueue :: a -> Queue a -> Queue a
enqueue x Empty      = Node x Empty
enqueue x (Node a n) = (Node a (enqueue x n)) 

{-
   La siguiente instancia de `Arbitrary` es para enseñar a QuickCheck
   a generar `Queue` aleatorias. No hay que saber cómo hacerlo;
   siempre se facilita.
-}

instance Arbitrary a => Arbitrary (Queue a) where
  arbitrary =  do
                xs <- listOf arbitrary
                return (foldr enqueue empty xs)
