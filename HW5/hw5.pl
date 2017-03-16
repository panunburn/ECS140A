/*******************************************/
/**    Your solution goes in this file    **/ 
/*******************************************/

fc_course(Course) :-
	course(Course, _, Units),
	(Units = 3;
	Units = 4).

prereq_110(Course) :-
	course(Course, L, _),
	member(ecs110, L).

ecs140a_students(Name) :-
	student(Name, L),
	member(ecs140a, L).

instructor_names(Name) :-
	student(john, L2),
	instructor(Name, L1),
	same_factor(L1, L2).

same_factor(L1, L2) :-
	member(Class, L1),
	member(Class, L2),
	!.

students(STUDENTS) :-
	instructor(jim, Jim_C),
	student(STUDENTS, L),
	same_factor(L, Jim_C).

allprereq(C_Name, Prereq) :- findall(
	Name,
	(course(Name, _, _),
	isprereq(Name, C_Name)),
	Prereq).

isprereq(Course1, Course2) :- /** check if course1 is the prereq of course2 **/
	course(Course2, Pre, _),
	(member(Course1, Pre);
	member(Course3, Pre),
	isprereq(Course1, Course3)),!.

all_length(L, X) :-
	L = [],
	X is 0,!.

all_length(L, X) :-
	atomic(L),
	X is 1,!.

all_length(L, X) :-
	L = [H|R],
	all_length(H, Y),
	all_length(R, Z),
	is_empty(Y, T),
	X is Y + Z + T.

is_empty(Y,X) :-
	Y = 0,
	X is 1,!.

is_empty(Y,X) :-
	X is 0.

equal_a_b(L) :-
	count(a, X, L),
	count(b, Y, L),
	X = Y.

count(S, X, L) :-
	L = [],
	X is 0,!.

count(S, X, L) :-
	S = L,
	X is 1,!.

count(S, X, L) :-
	atomic(L),
	\+ (S = L),
	X is 0,!.

count(S, X, L) :-
	L = [H|R],
	count(S, Y, H),
	count(S, Z, R),
	X is Y + Z.

swap_prefix_suffix(K, L, S) :-
	prefix(Pre, L),
	suffix(Suf, L),
	append(Pre, K, L1),
	append(L1, Suf, L),
	append(Suf, K, L2),
	append(L2, Pre, S).

palin(A) :-
	myreverse(A, L),
	compare(A, L).

myreverse([], []).

myreverse([H|T], Z) :-
	myreverse(T, Z1),
	append(Z1, [H], Z).

compare([], []).

compare([H1|R1], [H2|R2]) :-
	H1 = H2,
	compare(R1, R2).

good([0]).

good([1| R]) :-
	append(A, B, R),
	good(A),
	good(B),!.

state(left, left, right, left). /** goat on the right **/

arc(take(wolf, A, B), state(A, A, C, D), state(B, B, C, D)) :-
	opposite(A, B),
	safe(state(B,B,C,D)).

arc(take(goat, A, B), state(A, C, A, D), state(B, C, B, D)) :-
	opposite(A, B),
	safe(state(B,C,B,D)).

arc(take(cabbage, A, B), state(A, C, D, A), state(B, C, D, B)) :-
	opposite(A, B),
	safe(state(B, C, D, B)).

arc(take(none, A, B), state(A, C, D, E), state(B, C, D, E)) :-
	opposite(A, B),
	safe(state(B,C,D,E)).

arc(take(nothing, A, A), state(A, B, C, D), state(A, B, C, D)) :-
	/*write('Backtrack:'), write(A), write(B), write(C), write(D), nl,*/ fail.

opposite(left, right).
opposite(right, left).

unsafe(state(A, B, B, C)) :-
	opposite(A, B).
unsafe(state(A, C, B, B)) :-
	opposite(A, B).

safe(A) :-
	\+ (unsafe(A)).

printtake([]).
printtake([H|[]]):- !.
printtake(L) :-
	L = [H|R],
	H = state(A, C, D, E),
	R = [H1|R1],
	((H1 = state(B, F, D, E),
	writewolf(C, F));
	(H1 = state(B, C, G, E),
	writegoat(D, G));
	(H1 = state(B, C, D, M),
	writecabbage(E, M));
	(H1 = state(B, C, D, E),
	writenone(A,B))),
	printtake(R).

writewolf(C, F) :-
	opposite(C,F),
	write(take(wolf, C, F)), nl,
	!.

writegoat(D, G) :-
	opposite(D,G),
	write(take(goat, D, G)), nl,
	!.

writecabbage(E, M) :-
	opposite(E,M),
	write(take(cabbage, E, M)), nl,
	!.

writenone(A, B) :-
	write(take(none, A, B)), nl,
	!.

go(A, A, T) :-
	/*write(T),nl,*/
	myreverse(T, Z),
	/*write(Z),nl,*/
	printtake(Z);
	!.

go(A, B, T) :-
	arc(N, A, C),
	legal(C, T),
	go(C, B, [C|T]),!.

legal(C, T) :-
	\+ (member(C, T)).

solve:-
	go(state(left, left, left, left), state(right, right, right, right), [state(left, left, left, left)]).
