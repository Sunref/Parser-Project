   PROGRAM 44
   LDGADDR 0
   LDCINT 0
   STOREW
L0:
   LDGADDR 0
   LOADW
   LDCINT 10
   CMP
   BGE L1
   LDGADDR 4
   LDCINT 2
   LDGADDR 0
   LOADW
   MUL
   STOREW
   LDGADDR 0
   LDGADDR 0
   LOADW
   LDCINT 1
   ADD
   STOREW
   BR L0
L1:
   LDGADDR 0
   LDCINT 0
   STOREW
L4:
   LDGADDR 0
   LOADW
   LDCINT 10
   CMP
   BGE L5
   LDGADDR 4
   LOADW
   PUTINT
   PUTEOL
   LDGADDR 0
   LDGADDR 0
   LOADW
   LDCINT 1
   ADD
   STOREW
   BR L4
L5:
   HALT
