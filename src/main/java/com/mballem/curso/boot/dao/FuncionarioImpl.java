package com.mballem.curso.boot.dao;

import org.springframework.stereotype.Repository;

import com.mballem.curso.boot.domein.Funcionario;

@Repository
public class FuncionarioImpl extends AbstractDao<Funcionario, Long> implements FuncionarioDao{

}
