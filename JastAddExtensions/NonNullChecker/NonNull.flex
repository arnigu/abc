<YYINITIAL> {
  "@NonNull"                    { return sym(Terminals.NOTNULL); }
  "/*@NonNull*/"                    { return sym(Terminals.NOTNULL); }
  "@Raw"                        { return sym(Terminals.RAW); }
  "/*@Raw*/"                        { return sym(Terminals.RAW); }
  "@RawThis"                    { return sym(Terminals.RAWTHIS); }
  "/*@RawThis*/"                    { return sym(Terminals.RAWTHIS); }
}
