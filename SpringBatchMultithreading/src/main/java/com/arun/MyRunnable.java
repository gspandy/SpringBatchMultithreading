package com.arun;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyRunnable implements Runnable {

	@Override
	public void run() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		JobLauncher launcher = ctx.getBean("jobLauncher", JobLauncher.class);
		Job job = ctx.getBean("cricketjob", Job.class);

		try {
			synchronized (job) {
				JobExecution execute = launcher.run(job, new JobParameters());
				System.out.println(execute.getStatus());
			}

		} catch (JobExecutionAlreadyRunningException e) {
			e.printStackTrace();
		} catch (JobRestartException e) {
			e.printStackTrace();
		} catch (JobInstanceAlreadyCompleteException e) {
			e.printStackTrace();
		} catch (JobParametersInvalidException e) {
			e.printStackTrace();
		}

	}

}
