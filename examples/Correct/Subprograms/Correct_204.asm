   PROGRAM 4
   BR L1
L0:
   PROC 0
   LDLADDR -4
   LDLADDR -4
   LOADW
   LDCINT 1
   ADD
   STOREW
   LDCSTR "during p:  n = "
   PUTSTR
   LDLADDR -4
   LOADW
   PUTINT
   PUTEOL
   RET 4
L1:
   LDGADDR 0
   LDCINT 5
   STOREW
   LDCSTR "before p:  n = "
   PUTSTR
   LDGADDR 0
   LOADW
   PUTINT
   PUTEOL
   LDGADDR 0
   LOADW
   CALL L0
   LDCSTR " after p:  n = "
   PUTSTR
   LDGADDR 0
   LOADW
   PUTINT
   PUTEOL
   HALT
