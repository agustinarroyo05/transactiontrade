package com.transactiontrade.trading.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class TransactionFilter implements Filter {
	
		@Override
		public void init(FilterConfig filterConfig) throws ServletException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void doFilter(ServletRequest request, javax.servlet.ServletResponse response, FilterChain chain)
				throws IOException, ServletException {
		    HttpServletRequest req = (HttpServletRequest) request;
		    req.getSession();
		    chain.doFilter(request, response);
			
		}

		@Override
		public void destroy() {
			// TODO Auto-generated method stub
			
		}

}
