package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.Issue;
import ru.stqa.pft.mantis.model.Project;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class SoapTests extends TestBase {
    @Test
    public void testGetProjects() throws MalformedURLException,  RemoteException, javax.xml.rpc.ServiceException {
        Set<Project> projects = app.soap().getProjects();
        System.out.println(projects.size());
        for (Project project : projects) {
            System.out.println(project.getName());
        }
    }
    @Test
    public void testCreateIssue() throws MalformedURLException, RemoteException, javax.xml.rpc.ServiceException {
        Set<Project> projects = app.soap().getProjects();
        Issue issue = new Issue().withSummary("Test issue")
                                 .withDescription("Test issue description").withProject(projects.iterator().next());
        Issue created = app.soap().addIssue(issue);
        assertEquals(issue.getSummary(), created.getSummary());
    }

    @Test
    public void testCreateIssueWithSkip() throws MalformedURLException, RemoteException, javax.xml.rpc.ServiceException {
        skipIfNotFixed(1);
        Set<Project> projects = app.soap().getProjects();
        Issue issue = new Issue().withSummary("Bug report").withDescription("Description")
                                 .withProject(projects.iterator().next());
        Issue created = app.soap().addIssue(issue);
        assertEquals(issue.getSummary(), created.getSummary());
    }
}
