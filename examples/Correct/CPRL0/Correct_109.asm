   PROGRAM 4
   LDGADDR 0
   LDCINT 1
   STOREW
L0:
   LDGADDR 0
   LDGADDR 0
   LOADW
   LDCINT 1
   ADD
   STOREW
   LDGADDR 0
   LOADW
   LDCINT 6
   CMP
   BNZ L4
   BR L1
L4:
L5:
   BR L0
L1:
   LDCSTR "x = "
   PUTSTR
   LDGADDR 0
   LOADW
   PUTINT
   PUTEOL
   HALT
