(**1-CHECKED**)
let rec pow x n =
  match n with
  | 0 -> 1
  | n -> x*pow x (n-1);;

(* print_int(pow 2 5);; *)
(* print_int(pow 2 5);; *)
let rec map f lst =
  match lst with
  | [] -> []
  | h::t -> 
    let a = f h in 
    a :: map f t;;

let rec powerset = function
  | [] -> [[]]
  | h :: t -> 
    let p = powerset t in 
    p @ List.map (fun b -> h :: b) p;;


 (*compress - remove consecutive duplicates from a list*)
let rec compress lst = 
  match lst with
  | [] -> []
  | [a] -> [a]
  | a::b::rest -> if a = b then
    compress(b::rest)
  else
    a::compress(b::rest);;

    let prime n =
      let rec check a b=
        match b with
        | 1 -> true
        | _ -> check a (b-1) && (a mod b <> 0) in
        match n with
        | 0 -> false
        | 1 -> false
        | _ -> check n (n-1);;
    
(*slice lst i j - extract list containing elements from ith-inclusive to jth-exclusive*)
let rec delete n = function
| [] -> []
| h::t -> if n=0 then
    h::t
  else
    delete (n-1) t;;

let rec keep n = function
| [] -> []
| h::t -> if n=0 then
    []
  else 
    h::keep (n-1) t;;

let slice lst i j =
  if j <= i then
    []
  else keep (j-i) (delete i lst);;

  
let rec fibonacci n =
  if n < 3 then 1
  else fibonacci (n-1) + fibonacci (n-2);;



let rec float_pow x n =
  match n with
  | 0 -> 1.
  | n -> x *. float_pow x (n-1);;

(**2-CHECKED**)


(**3-CHECKED**)
(*remove_if - removes everything that satisfy the predicate*)
let rec remove_if f lst = 
  match lst with
  | [] -> []
  | h::t -> if f h then
    remove_if f t else
      h::remove_if f t;;


(**4-CHECKED**)


(*5-CHECKED*)
(**equivs - partition list into equivalance classes according to
    equivalence function**)
  let rec equal f x input =
    match input with
    | [] -> []
    | h :: t -> if (f x h) then
        h :: equal f x t
    else 
        equal f x t;;

    let rec nequal f x input =
      match input with
      | [] -> []
      | h :: t -> if (f x h) then
          nequal f x t
      else 
          h::nequal f x t;;

  let rec eqHelper f input results =
    match input with
    | [] -> results
    | h::t -> eqHelper f (nequal f h input) ((equal f h input)::results);;


  let rev list =
    let rec aux lst = function
    | [] -> lst
    | h::t -> aux (h::lst) t in 
      aux [] list ;;

  let rec equivs f lst = 
    rev(eqHelper f lst []);;
  

(*6-CHECKED*)
let prime n =
  let rec check a b=
    match b with
    | 1 -> true
    | _ -> check a (b-1) && (a mod b <> 0) in
    match n with
    | 0 -> false
    | 1 -> false
    | _ -> check n (n-1);;

let goldbachpair x =
  let rec gHelper d =
    if prime d && prime (x - d) then 
      (d, x - d)
    else 
      gHelper (d + 1)
      in
  match x with
  | 0 -> (0,0)
  | _ -> gHelper 2;;



(*7-CHECKED*)
let rec equiv_on f g list =
  match list with
| [] -> true
| h :: t -> if f h = g h then 
    equiv_on f g t
  else
    false;;

(*8-CHECKED*)
let rec pairwisefilter cmp = function
|[] -> []
|[a] -> [a]
|a::b::t -> if cmp a b = a then
    a::pairwisefilter cmp t 
  else 
    b::pairwisefilter cmp t;;

(*9-CHECKED*)
let rec polyHelper x = function
| [] -> 0
| h::t -> (fst h)*pow x (snd h) + polyHelper x t;;
  
let polynomial lst n =
  polyHelper n lst;;
  
(*10-CHECKED*)
let rec map f lst =
  match lst with
  | [] -> []
  | h::t -> 
    let a = f h in 
    a :: map f t;;









