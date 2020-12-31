package com.ernesto.moneyapi.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

      List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesevolvedor));
      return handleExceptionInternal(ex, erros, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
        HttpHeaders headers, HttpStatus status, WebRequest request) {

          List<Erro> erros = criarListaDeErros(ex.getBindingResult());
          return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
    }

    private List<Erro> criarListaDeErros(BindingResult bindigResult) {
      List<Erro> erros = new ArrayList<>();

      for (FieldError fieldError : bindigResult.getFieldErrors()) {
        String mensagemUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
        String mensagemDesevolvedor = fieldError.toString();
        erros.add(new Erro(mensagemUsuario, mensagemDesevolvedor));

      }

      return erros;
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
