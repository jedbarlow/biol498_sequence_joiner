DNA Sequence Joiner
===================

This project is a plugin for [CLC Genomics Workbench](http://www.clcbio.com/products/clc-genomics-workbench/)
that joins DNA sequences, optionally inserting a custom delimiter sequence between them.

For example, given a list of sequences,

  AAAAAAA
  BBBBBBB
  CCCCCCC

and entering a delimiter

  NNNN

the plugin produces the following output.

  AAAAAAANNNNBBBBBBBNNNNCCCCCCCNNNN
