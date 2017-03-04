;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Your solution goes in this file ;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defun all-length (x)
        (cond
		((null x) 0)
		((listp (car x)) (+ (all-length (car x)) (all-length (cdr x))))
		((atom (car x)) (+ 1 (all-length (cdr x))))
	)
)

(defun range (x)
	(list (apply 'min x) (apply 'max x))
)

(defun before (a b l)
	(cond
		((null l) nil)
		((equal a (car l)) (append (list (car l)) (rem_list b (cdr l))))
		(t (before a b (cdr l)))
	)
)

(defun rem_list (b l)
	(cond
		((null l) nil)
		((equal b (car l)) (cons (car l) nil))
		(t (cons (car l) (rem_list b (cdr l))))
	)
)

(defun split-if (f l)
	(list (judge-f f l) (judge-t f l))
)

(defun judge-t (f l)
	(cond
		((null l) nil)
		((apply f (list (car l))) (cons (car l) (judge-t f (cdr l))))
		(t (judge-t f (cdr l)))
	)
)

(defun judge-f (f l)
	(cond
		((null l) nil)
		((not(apply f (list (car l)))) (cons (car l) (judge-f f (cdr l))))
		(t (judge-f f (cdr l)))
	)
)

(defun group (l n)
	(cond
		((null l) nil)
		((eql n 0) nil)
		(t (sub-group nil l n))
	)
)

(defun sub-group (temp l n)
	(cond	
		((and (null l) (not(null temp))) (list temp))
		((null l) nil)
		((equal (length temp) n) (append (list temp) (sub-group nil l n)))
		((> (length temp) n) nil)
		(t (sub-group (append temp (list (car l))) (cdr l) n))
	)
)

(defun mostn (f l)
	(setq score (mapcar f l))
	(setq max (apply 'max score))
	(list (most-f f l max) max)
)

;(defun most-s (f l)
;	(setq left (funcall f (car l)))
;	(setq right (most-s f (cdr l)))
;	(cond
;		((> left right) left)
;		(t right)
;	)
;)

(defun most-f (f l max)
	(cond
		((null l) nil)
		((equal (funcall f (car l)) max) (cons (car l) (most-f f (cdr l) max)))
		(t (most-f f (cdr l) max))
	)
)

(defun match (a b)
	(cond
		((exclamation a) (match-s a b))
		(t (match-s a b))
	)
)

(defun match-var-s (a b state)
	(cond
		((and (null a) (null b)) t)
		((or (null a) (null b)) nil)
		((and (listp (car a)) (not(null (car a))))
			(cond
				((not(equal 2 (length (car a)))) nil)
				((equal '? (caar a))
					(cond
						((match-var-s (cdr a) (cdr b) state) (set (cadar a) (car b)) t)
						(t nil)
					))
				((equal '! (caar a))
					(cond
						((or (equal state 0) (equal state 1)) (set (cadar a) (list (car b))))
						(t (set (cadar a) (append (eval (cadar a)) (list (car b)))))
					)
					(cond
						((match-var-s (cdr a) (cdr b) 1) t)
						((match-var-s a (cdr b) 2) t)
						(t (set (cadar a) nil) nil)
					))
			))
		(t (and (equal (car a) (car b)) (match-var-s (cdr a) (cdr b) state)))
	)
)

(defun match-var (a b)
	(match-var-s a b 0)
)

(defun exclamation (a)
	(cond
		((null a) nil)
		((equal '! (car a)) t)
		(t (exclamation (cdr a)))
	)
)

(defun match-s (a b)
	(cond
		((and (null a) (null b)) t)
		((or (null a) (null b)) nil)
		((equal '?  (car a)) (match-s (cdr a) (cdr b)))
		((equal '! (car a))
			(cond
				((match-s (cdr a) (cdr b)) t)
				(t (match-s a (cdr b)))
			)
		)
		(t (and (equal (car a) (car b)) (match-s (cdr a) (cdr b))))
	)
)

;(defun rem-sym (a)
;	(cond
;		((null a) t)
;		((equal (car a) '!) (rem-sym (cdr a)))
;		(t a)
;	)
;)

;(defun next-sym (b sym)
;	(cond
;		((null b) nil)
;		((equal (car b) sym) b)
;		(t (next-sym (cdr b)))
;	)
;)
