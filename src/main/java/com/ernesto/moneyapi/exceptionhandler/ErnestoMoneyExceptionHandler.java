package com.ernesto.moneyapi.exceptionhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErnestoMoneyExceptionHandler extends ResponseEntityExceptionHandler  {

  @Autowired
  private MessageSource messageSource;

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
    HttpHeaders headers, HttpStatus status, WebRequest request) {

      String mensagemUsuario = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
      String mensagemDesevolvedor = ex.getCause().toString();
      return handleExceptionInternal(ex, new Erro(mensagemUsuario, mensagemDesevolvedor), headers, status, request);
    }

    private static class Erro {

      private String mensagemUsuario;
      private String mensagemDesevolvedor;

      public Erro(String mensagemUsuario, String mensagemDesevolvedor) {
        this.mensagemUsuario = mensagemUsuario;
        this.mensagemDesevolvedor = mensagemDesevolvedor;
      }

      public String getMensagemUsuario() {
        return mensagemUsuario;
      }

      public String getMensagemDesevolvedor() {
        return mensagemDesevolvedor;
      }

    }
}