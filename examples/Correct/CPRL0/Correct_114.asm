   PROGRAM 8
   LDCSTR "Enter value for i: "
   PUTSTR
   LDGADDR 0
   GETINT
   STOREW
   LDCSTR "Enter value for j: "
   PUTSTR
   LDGADDR 4
   GETINT
   STOREW
L0:
   LDGADDR 0
   LOADW
   LDCINT 10
   CMP
   BG L1
L4:
   LDGADDR 4
   LOADW
   LDCINT 10
   CMP
   BG L5
   LDGADDR 0
   LDGADDR 0
   LOADW
   LDCINT 1
   ADD
   STOREW
   LDGADDR 4
   LDGADDR 4
   LOADW
   LDCINT 2
   ADD
   STOREW
   BR L4
L5:
   LDGADDR 0
   LDGADDR 0
   LOADW
   LDCINT 3
   ADD
   STOREW
   BR L0
L1:
   LDCSTR "i = "
   PUTSTR
   LDGADDR 0
   LOADW
   PUTINT
   LDCSTR ", j = "
   PUTSTR
   LDGADDR 4
   LOADW
   PUTINT
   PUTEOL
   HALT
