package com.mayab.quality.doubles;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

class DependencyTest {

	
	private SubDependency subDependency;
	private Dependency dependency;
	public static final int TEST = 3;
	
	@BeforeEach
	void setup() {
		subDependency = mock(SubDependency.class);
		dependency = mock(Dependency.class);
	}
	@Test
	void getClassName() {
		String name = dependency.getSubDependencyClassName();
		assertThat(name,is("SubDependency.class"));
	}
	@Test
	void getAddTwo() {
		int num = 10;
		int expected = 3;
		
		when(dependency.addTwo(anyInt())).thenReturn(TEST);
		int result = dependency.addTwo(1);
		
		assertThat(expected, is(result));
	}

	@Test
    public void testAnswer() {

        when(dependency.addTwo(anyInt())).thenAnswer(new Answer<Integer>() {
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                int arg = (Integer) invocation.getArguments()[0];
                return arg + 20;
            }
        });
        assertEquals(30, dependency.addTwo(10));
    }
}

