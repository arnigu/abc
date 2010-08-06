#!/bin/bash

echo "aspect UndoSettersAutogenerated {"
echo "// AUTOGENERATED"

function IDrecursion {
	PATTERN="$1"
	NAME="$2"
	VAR="$3"
	TP="$4"
	SOFAR="$5"
	ASPEC="$6"


	if [ ! -z "$7" ]; then
		# we have an agrument, looking for inherited
		II=`find ../../Java* ../../Refactoring -name "*.ast" | xargs grep -E "\\ :\\ $7\\ " | grep -vE "$PATTERN" | sed 's/abstract //' | cut -f1 -d' ' | sed -r 's/^[^:]*\///'`
	else
		II=`find ../../Java* ../../Refactoring -name "*.ast" | xargs grep -E "$PATTERN" | sed 's/abstract //' | cut -f1 -d' ' | sed -r 's/^[^:]*\///'`
	fi;

	for i in $II; do

		#echo "5:$7"
		ASPECT=`echo -n $i | sed -r 's/\.ast:.*//'`
		if [ ! -z "$7" ]; then
			ASPECT="$ASPEC"
		fi;
		TYPE=`echo -n $i | sed -r 's/[^.]*\.ast://'`
		if [ "`echo "$SOFAR" | grep "$TYPE" | wc -l`" != "0" ]; then
			continue;
		fi;

		echo "
	refine $ASPECT public void $TYPE.set$NAME($TP value) {
		Program root = (Program) programRootParentFromField();
		if (root != null && root.isRecordingUndo()) {
			final $TP v = $VAR;
			root.addUndoAction(new ASTModification() {
				public void undo() {
					refined(v);
				}
			});
		}
		refined(value);
	}
	"
		IDrecursion "$PATTERN" "$NAME" "$VAR" "$TP" "$SOFAR\n$TYPE" "$ASPECT" "$TYPE"
	done;
}
IDrecursion "(<ID:(java\\.lang\\.)?String>|<ID>)" "ID" "tokenString_ID" "String" "" ""
IDrecursion "(<Name:(java\\.lang\\.)?String>|<Name>)" "Name" "tokenString_Name" "String" "" ""
IDrecursion "(<Package:(java\\.lang\\.)?String>|<Package>)" "Package" "tokenString_Package" "String" "" ""

#for i in `find ../../Java* ../../Refactoring -name "*.ast" | xargs grep -E "<Name:String>|<Name>" | sed 's/abstract //' | cut -f1 -d' ' | sed -r 's/^[^:]*\///'`; do
#
#	ASPECT=`echo -n $i | sed -r 's/\.ast:.*//'`
#	TYPE=`echo -n $i | sed -r 's/[^.]*\.ast://'`
#	echo "
#	refine $ASPECT public void $TYPE.setName(String value) {
#		Program root = (Program) programRootParentFromField();
#		if (root != null && root.isRecordingUndo()) {
#			final String v = tokenString_Name;
#			root.addUndoAction(new ASTModification() {
#				public void undo() {
#					refined(v);
#				}
#			});
#		}
#		refined(value);
#	}
#	"
#done;

echo "}"
