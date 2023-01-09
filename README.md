# Sample AEM project template

This is a project template for AEM-based applications. It is intended as a best-practice set of examples as well as a potential starting point to develop your own functionality.

## Modules

The main parts of the template are:

* core: Java bundle containing all core functionality like OSGi services, listeners or schedulers, as well as component-related Java code such as servlets or request filters.
* it.tests: Java based integration tests
* ui.apps: contains the /apps (and /etc) parts of the project, ie JS&CSS clientlibs, components, and templates
* ui.content: contains sample content using the components from the ui.apps
* ui.config: contains runmode specific OSGi configs for the project
* ui.frontend: an optional dedicated front-end build mechanism (Angular, React or general Webpack project)
* ui.tests: Selenium based UI tests
* all: a single content package that embeds all of the compiled modules (bundles and content packages) including any vendor dependencies
* analyse: this module runs analysis on the project which provides additional validation for deploying into AEMaaCS

## How to build

To build all the modules run in the project root directory the following command with Maven 3:

    mvn clean install

To build all the modules and deploy the `all` package to a local instance of AEM, run in the project root directory the following command:

    mvn clean install -PautoInstallSinglePackage

Or to deploy it to a publish instance, run

    mvn clean install -PautoInstallSinglePackagePublish

Or alternatively

    mvn clean install -PautoInstallSinglePackage -Daem.port=4503

Or to deploy only the bundle to the author, run

    mvn clean install -PautoInstallBundle

Or to deploy only a single content package, run in the sub-module directory (i.e `ui.apps`)

    mvn clean install -PautoInstallPackage

## Testing

There are three levels of testing contained in the project:

### Unit tests

This show-cases classic unit testing of the code contained in the bundle. To
test, execute:

    mvn clean test

### Integration tests

This allows running integration tests that exercise the capabilities of AEM via
HTTP calls to its API. To run the integration tests, run:

    mvn clean verify -Plocal

Test classes must be saved in the `src/main/java` directory (or any of its
subdirectories), and must be contained in files matching the pattern `*IT.java`.

The configuration provides sensible defaults for a typical local installation of
AEM. If you want to point the integration tests to different AEM author and
publish instances, you can use the following system properties via Maven's `-D`
flag.

| Property | Description | Default value |
| --- | --- | --- |
| `it.author.url` | URL of the author instance | `http://localhost:4502` |
| `it.author.user` | Admin user for the author instance | `admin` |
| `it.author.password` | Password of the admin user for the author instance | `admin` |
| `it.publish.url` | URL of the publish instance | `http://localhost:4503` |
| `it.publish.user` | Admin user for the publish instance | `admin` |
| `it.publish.password` | Password of the admin user for the publish instance | `admin` |

The integration tests in this archetype use the [AEM Testing
Clients](https://github.com/adobe/aem-testing-clients) and showcase some
recommended [best
practices](https://github.com/adobe/aem-testing-clients/wiki/Best-practices) to
be put in use when writing integration tests for AEM.

## Static Analysis

The `analyse` module performs static analysis on the project for deploying into AEMaaCS. It is automatically
run when executing

    mvn clean install

from the project root directory. Additional information about this analysis and how to further configure it
can be found here https://github.com/adobe/aemanalyser-maven-plugin

### UI tests

They will test the UI layer of your AEM application using Selenium technology. 

To run them locally:

    mvn clean verify -Pui-tests-local-execution

This default command requires:
* an AEM author instance available at http://localhost:4502 (with the whole project built and deployed on it, see `How to build` section above)
* Chrome browser installed at default location

Check README file in `ui.tests` module for more details.

## ClientLibs

The frontend module is made available using an [AEM ClientLib](https://helpx.adobe.com/experience-manager/6-5/sites/developing/using/clientlibs.html). When executing the NPM build script, the app is built and the [`aem-clientlib-generator`](https://github.com/wcm-io-frontend/aem-clientlib-generator) package takes the resulting build output and transforms it into such a ClientLib.

A ClientLib will consist of the following files and directories:

- `css/`: CSS files which can be requested in the HTML
- `css.txt` (tells AEM the order and names of files in `css/` so they can be merged)
- `js/`: JavaScript files which can be requested in the HTML
- `js.txt` (tells AEM the order and names of files in `js/` so they can be merged
- `resources/`: Source maps, non-entrypoint code chunks (resulting from code splitting), static assets (e.g. icons), etc.

## Maven settings

The project comes with the auto-public repository configured. To setup the repository in your Maven settings, refer to:

    http://helpx.adobe.com/experience-manager/kb/SetUpTheAdobeMavenRepository.html
1. create an OSGi bundle which has OSGi service which reads JSON data using HTTP GET request from the URL

## Notes
--------JAVA OOPs Concepts---------
1. Explain briefly OOPs Concept? - Object
   Class,Inheritance,Polymorphism,Abstraction,Encapsulation

2. Why static keyword is used? - static keyword is used for memory management mainly. We can apply static keyword with variables, methods
   The static variable can be used to refer to the common property of all objects (eg: static String college="IELTS")

3. Can a class extend multiple class? - Multiple inheritance is not allowed.

------------------Collections-----------
1. Basic interfaces of the Java Collection Framework? - Set,List,Collection

2. ArrayList vs LinkedList?- ArrayList is preferred when storing and accessing is required while Linked list is prefered when manipulation is required.

3. Why do we use Map in Java?

----------------Exception-------
1. How the exceptions are handled in java?using try, catch and finally blocks
2. what are checked and unchecked exceptions in java? Checked exceptions are the exceptions which are known to compiler.These exceptions are checked at compile time only. ClassNotFoundException, SQLException, IOException

                                                      Unchecked exceptions are those exceptions which are not at all known to compiler. These exceptions occur only at run time.NullPointerException, ArrayIndexOutOfBoundsException, NumberFormatException

----------------Stream API------
1. Stream API is used to process collections of objects.
2. Using stream API , get the list starting with "S"
   List names = Arrays.asList("Reflection","Collection","Stream");
   List result = names.stream().filter(s->s.startsWith("S")).collect(Collectors.toList());												  
   3.Lambda expressions are similar to methods, but they do not need a name and they can be implemented right in the body of a method.
   4.Serialization is a mechanism of converting the state of an object into a byte stream.

---------HTML/CSS/JavaScript---------
1. difference between  let and const.
2. forEach vs Map?
3. worked on javascript library like React or framework like Angular.
   4.jQuery is a popular library for simiplifying interactions between DOM and JavaScript

--------REST API------------
Worked on REST API?

---------AEM--------
1.What is maven archetype/How do u create project template in AEM?- The AEM Project Archetype is a Maven template that creates a minimal, best-practices-based Adobe Experience Manager.
2.AEM architecture stack of AEM.
3.Have you worked on sling models? efaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
@Model(adaptables = SlingHttpServletRequest.class,
adapters = AccordionCmp.class,
defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

4.
@Model(adaptables = Resource.class)
public class TestModel {

    // Injects the child of the resource using ChildResource annotation
    @ChildResource(name="content")
    Resource child;

    public String getChildPath() {
      return  child.getPath();
    }
}
ChildResource : This injector is adaptable to resource and is used to get the specific child of a resource.

5. Script Variable: This Injector is used to get the currentPage, PageManager, Design, PageProperties etc.
   This injector is adaptable to request.This injector is adaptable to SlingHttpServletRequest.
   @Model(adaptables = SlingHttpServletRequest.class)
   public class TestModel {

// Injects currentPage using ScriptVariable annotation
@ScriptVariable(name="currentPage")
Page page;

    public String getPagePath() {
        return  page.getPath();
    }
}

6. Template Types are effectively a template for a template.

7. Where is template type stored:
   /conf/wknd/settings/wcm/template-types/empty-page/structure/jcr:content
   8.responsive layout for your pages??
   AEM allows you to have a responsive layout for your pages by using the Layout Container component.
   This provides a paragraph system that allows you to position components within a responsive grid.
   This grid can rearrange the layout according to the device/window size and format.

9.Style System?
Template authors not only need the ability to configure how components function for the content authors,
but also to configure a number of alternative visual variations of a component.


4.Diff btw Felix SCR annotations and OSGi R6 annotations?
5. How do u create configurable service in AEM using DS annotations.
6. How do u create servlet using DS?
   @Component(
   service = Servlet.class,
   property = {
   "sling.servlet.extensions=html",
   "sling.servlet.selectors=foo",
   "sling.servlet.paths=/bin/foo",
   "sling.servlet.paths=/bin/bar",
   "sling.servlet.methods=get",
   "sling.servlet.resourceTypes=nt:file",
   "sling.servlet.resourceTypes=project/components/component"
   }
   )
7. Worked on Event Listener- We can achieve event handling at the JCR level by implementing the EventListener interface in a class.Override onEvent method.
   @Component(service = EventListener.class, immediate = true)
   public class CustomEventListener implements EventListener {...}
8. How do i get the resource progammatically in AEM.
9.  Sling API------Resource
    JCR API----Node
    adaptTo = Sling comes with adapters mechanism. One object can be adopted to other.
    ValueMap properties = resource.adaptTo(ValueMap.class);
    String property = properties.get("myProperty", String.class);

Authoring skills
Overlay components
Customer components
Editable templates
crxde lite
Clientlibs
Sightly
Workflows real life use cases
Replication Agents, reverse replication real life use cases
Configuration console & crxde lite for configurations
Custom looging
MSM

Should have done Dispatcher configurations or modified
Adobe marketing cloud integration - Adobe I/O, AEM & Java code
At least one AEM upgrade project or Service Pack Updgrade
Reports and dashboards
Knowdlege on version differences (preferrably 6.5/6.4/6.3 & cloud services)
Basic Web Technology concepts: Session, cookies, http, https, smtp etc
Knowledge on Security and Performance considerations

A developer needs to change the label “Sites” in the navigation of AEM author to “Websites”
Modify the node /libs/cq/core/content/nav/sites by updating the icr:title property value.

A developer creates an AEM editable template that includes a Layout Container.
When the developer creates a page using this template, the Layout Container placeholder does NOT appear.
The Layout Container has NOT been unlocked.

You want to restrict the size of a custom log file and enable log file rotation. Which Apache
Sling configuration has to be changed in the AEM Web Console?
Apache Sling Logging Logger Configuration.

The model must check if the configured value of the jcr:title property for the component matches the name of the current page. If the jcr:title property of the component has NOT been configured, then isMatchingTitle() must
return false

6. What are the requests not cached by the dispatcher?
   Request that does not return http code 200
   Requests with suffixes
   Requests with request parameter(i.e ?)
>Programmatically: send an http header response.setHeader("Dispatcher", "no-cache")

18. How to prevent one particular component to be cached, while you are caching the whole page into dispatcher?
    We have to configure the component(which we don't want to be cached) into Sling Dynamic Include (SDI).

The /renders property defines the URL to which Dispatcher sends requests to render a document.
/renders {
/0001 {
/hostname "host-name-here"
/port "4502"
/ipv4 "1"
/always-resolve "1"
}
}

Permission-sensitive caching enables you to cache secured pages.
Dispatcher checks user’s access permissions for a page before delivering the cached page.
Dispatcher includes the AuthChecker module that implements permission-sensitive caching.
{
# request is sent to this URL with '?uri=<page>' appended
/url "/bin/permissioncheck"

# only the requested pages matching the filter section below are checked,
# all other pages get delivered unchecked
/filter
{
/0000
{
/glob "*"
/type "deny"
}
/0001
{
/glob "/content/secure/*.html"
/type "allow"
}
}
# any header line returned from the auth_checker's HEAD request matching
# the section below will be returned as well
/headers
{
/0000
{
/glob "*"
/type "deny"
}
/0001
{
/glob "Set-Cookie:*"
/type "allow"
}
}

A developer is creating a custom component on the page /latestBlogs.html that needs to list all the titles of the blogs pages under /content/blogs.
How does this component get the list of child pages?
Use PageManager.getPage(“/content/blogs”) of the static PageManager class to instantiate a Page object and then iterate through the child pages and print the title for each


A developer has a component located under the path /apps. This component has a Client Library which is directly loaded onto a page.
The publish instance loads the page correctly. The dispatcher also loads the page but the Client Library is missing.
How should the developer resolve this issue, while taking security into consideration?
Add the property allowProxy with a boolean value true

One of the most important meta files of a vault checkout or a content package is the filter.xml which is present in the META-INF/vault directory.
The filter.xml consists of a set of filter elements, each with a mandatory root attribute and an optional list of include and exclude child elements.
MERGE: Existing content is not modified, i.e. only new content is added and none is deleted or modified.
UPDATE: Existing content is updated, new content is added and none is deleted.
REPLACE: This is the normal behavior. Existing content is replaced completely by the imported content, i.e. is overridden or deleted accordingly.


How to run some configuration specific to AEM instance.
create node -> apps/project/config.author.dev
create child node -> type will be sling:OSGIConfig and name should be same as PID

Run  component on specific Environment eg. publish
Create component and annotate with policy =ConfigurationPolicy.REQUIRE
create node under config.publish-> com.sumit.MyComponentnz(slingOsgiConfig)

BundleA for author and Bundle B for publish.
install.author -> BundleA.jar
install.publish-> BundleB.jar


 <span data-sly-text="${properties.title}"> Some text</span>

 <span data-sly-attribute.title="${properties.title}" title="Lorem Ipsum"> Hello World</span>

 <h1 data-sly-element="p">Lorem Ipsum</h1>

data-sly-list vs data-sly-repeat

 <ul data-sly-repeat="${currentPage.listChildren}">

 </ul>

1. Assuming we have resource /x/y, resolve below URI:
   /x/y.s1.s2.html/a/b.s.txt		
   Resource - /x/y
   Selector - s1.s2
   Extension- html
   Suffix - /a/b.s.txt
   Resource Found - Yes

2. Using querybuilder- Search for pages tagged with a certain tag.
   type=cq:Page
   tagid=marketing:interest/product
   tagid.property=jcr:content/cq:tags

3. How to use QueryBuilder API

a. create query desc Hashmap
b. Query query = builder.createQuery(PredicateGroup.create(map), session);
c. SearchResult result = query.getResult();

4. How to include JSP in your HTL file.
   <sly data-sly-include="template.jsp"/>

5. Can you create a component without cq:dialog?
   Without cq:dialog the component cannot be added to page.


Problem Statement: In the Touch UI Dialogs ,If there is a need to put some validation on fields or we just want to add some CSS,then use “cq.authoring.dialog” clientLibrary.This clientlibs is automatically loaded when the dialog opens.So, No need to make an entry of it on any page.

This created a performance issue because if the clientLibrary is so big and it is getting loaded in all dialogs regardless of dialog needs it or not.

Solution:

Using extraClientlibs Property: In the cq:dialog node of a dialog, Add these properties:
1. Name:extraClientlibs
Type: String[]
Value: ClientLib_Category_Name
2. Name: mode
Type:String
Value:edit”

6. ----------------Sling Model Exporter--------------------

   @Exporter(name="exporter",extensions="type",selector="selector")		  
   name-> of the exporter
   extensions-> which format the exporter will return.
   selector->
   Eg: Jackson is OOTB Exporter provided by AEM:
   @Exporter(name="jackson",extensions="json",selector="geek")
   model - is default selector
   http://localhost:4502/content/sumit/us/en/magazine/prayagraj-skateboards/jcr:content/root/container/container/aemgeek.geek.json

6.1 It exports all getter method in JSON format.
6.2 To export non getter method, use @JsonProperty.
@JsonProperty(value="scholName")
public String school()
{
return "SMC";
}
6.3 To change the name of Json Key, we use @JsonProperty in getter method:

@JsonProperty(value="teacher")
public boolean getProfessor() {
return professor;
}
6.4 If any value is null, we use @JsonIgnore to not produce that json key.
6.5 To wrap all json object in some root json key. We use options attribute in @Exporter
and annotate the class with @JsonRootName("rootKey")
@Exporter(name="jackson",extensions="json",selector="geek",options={
@ExporterOption(name="SerializationFeature.WRAP_ROOT_VALUE",value="true")})
@JsonRootName("rootKey")
6.6 To write custom Sling Model Exporter- i.e XML exporter(We will use JAXB API)
We need to create AEM Service implementing ModelExporter.class and override 3 methods:
1. boolean isSupported
2. String getName
3. export
6.7 We can have multiple exporters with @Exporters option- Refer AEMGeekImpl.java

7.------------Service User-------------

7.1 Create System User using /crx/explorer/index.jsp and give permissions in user admin for this user.
Or Create a sysem user  and add permissions using ACS Commons Config -> Ensure Service User
7.2 Map this user in AEM config -> Apache Sling Service User Mapper Service Amendment
{bundleName}:{servicename}={serviceuser}
aem-sumit.core:sumitservice=sumit-system-user
7.3 Write Util class to get resource resolver. Refer ResourceResolverUtil.java

8.------------Logging--------------------

8.1 We have 5 log levels in order as:
Trace
Debug
Info
Warn
Error
8.2 Custom log files can be created to log specific package or class using config -> Apache Sling Logging Logger Configuration.
8.3 AEM provides console to view/add logs in browser -> http://localhost:4502/system/console/slinglog
8.4 AEM provides config to configure log rotation based on file size and time -> Apache Sling Logging Writer Configuration.

9------------OSGI Service--------------------

9.1 Service will always implement interface
Mandatory annotation -> @Component(service=SomeInterface.class)
Optional annotation -> @Activate , @Deactivate

9.2 When we have two implementation of same interface, we can define ranking of osgi service.

     MultiService(Interface) -> getName()
	 MultiServiceImplA() -> Implementation of getName()
	 MultiServiceImplB() -> Implementation of getName()
	 
	 When we use this service , by default priority will be given to service with lower Service ID.
     Service ID can be found in AEM console when we click core bundle.

9.3 But we can give ranking to each implementation to call them separately by annotating service with @ServiceRanking(1001).
eg:
@Component(service= MultiService,name=ServiceA)
@Ranking(1001)
MultiServiceImplA()

9.4 To call specific service in sling Model, we use filter attribute. Below calls MultiServiceImplB.
@OsgiService(filter="(component.name=ServiceB)")
MultiService service;

9.5 To call specific service in Component, we use target attribute.
@Reference(target="(component.name=ServiceB)")

10-----------OSGI COnfig using r7 annotation--------

10.1 Create @interface GeeksOSGiConfig and annotate with @ObjectClassDefinition(name="Modular OSGi Configuration",description = "Modular OSGi Configuration demo.")
This interface will map all values from configMgr using @AttributeDefinition
10.2 Create OSGI Service and annotate with @Designate(ocd=GeeksOSGiConfig.class) and inject above config in @Activate Method.

11-----------OSGI Factory Configuration-------------

When we need different instance of same OSGI configuration, we declare OSGI config as factory configuration.
This is achieved by adding attribute factory=true in @Designate .

11.1 Inside Impl we create new List:
private List<OSGiFactoryConfig> factoryConfigsList; //OSGiFactoryConfig -> is our interface with all config mapping
11.2 We add bind and unbind method as:

      @Reference(service = OSGiFactoryConfig.class,cardinality = ReferenceCardinality.MULTIPLE,policy = ReferencePolicy.DYNAMIC)
      public void bindOSGIFactoryConfig( final OSGiFactoryConfig osGiFactoryConfig) {
    
       factoryConfigsList.add(osGiFactoryConfig);
      }

      public void unbindOSGIFactoryConfig( final OSGiFactoryConfig osGiFactoryConfig) {
       factoryConfigsList.remove(osGiFactoryConfig);
      }
11.3 ReferenceCardinality.MULTIPLE -> This method will listen to service everytime.
ReferencePolicy.DYNAMIC -> It will find unbind method with same name automatically.

12----------------Sling Servlet---------------

Servlet can be registered using OSGi DS 1.4(R7) which needs bnd-maven-plugin 4.0+ -> @SlingServletResourceTypes ,@SlingServletPaths
Servlet can be registered using OSGi DS 1.2(RS) which needs mvn-bundle-plugin 3.0+ -> @Component(service=Servlet.class)
Servlets can be registered using resourceType or path.

12.1 Using resourceType: Servlet is bound to resource and can be access controlled using JCR repository ACL.
Servlets use methods,selector and extensions.

13---------------Sling Schedular-------------

Schedular can be registered by implementing either:
13.1 Runnable Interface -> implement run method
13.2 Job Interface -> implement execute(JobContext ctx)
13.3 Status of scheduler can be checked at http://localhost:4502/system/console/status-slingscheduler

We need to implement JobInterface if we want to run scheduler in different time intervals with different parameters.

14-------------QueryBuilder------------------

Two search API's(Repository Search) used in AEM are - QueryBuilder API and JCR SQL2
AEM Console to execute query -> http://localhost:4502/libs/cq/search/content/querydebug.html

14.1 Whatever we want to search we provide in predicate called type and value should be the primaryTpe.
Bydefault AEM will restrict result to 10 results but we can use p.limit = -1 to show all results.

14.2 Search all pages under given path
type = cq:Page
path = /content/sumit/
orderby = @jcr:content/cq:lastModified
orderby.sort = desc
p.limit= -1

14.3 To search a property we use property predicate.
Search all pages with given template
type = cq:Page
path = /content/sumit/
property = jcr:content/cq:template
property.value = /conf/sumit/settings/wcm/templates/page-content

14.4 To search multiple values with given property:
By default its an OR opertaion when we search single property. It will find either title is Magazine or title is FAQ's
Search all pages with jcr title Magazine or FAQs
type = cq:Page
path = /content/sumit/
property = jcr:content/jcr:title
property.1_value = Magazine
property.2_value = FAQs

	  To search with AND operation on same property -> property.and = true

14.5 To search multiple property:
By default its an AND operation when searching multiple property.
type = cq:Page
path = /content/sumit/
1_property = jcr:content/jcr:title
1_property.value = Magazine
2_property = jcr:content/cq:template
2_property.value = /conf/sumit/settings/wcm/templates/page-content

	  In order to apply OR operation while multiple property search operation , we use predicate group.
	  type = cq:Page
      path = /content/sumit/
	  group.p.or = true
      group.1_property = jcr:content/jcr:title
      group.1_property.value = Magazine
      group.2_property = jcr:content/cq:template
	  group.2_property.value = /conf/sumit/settings/wcm/templates/page-content

	  When we search with above query, you can see the default property returned by JCR in JSON QueryBuilder Link.
	  eg: For above query we see result as:
	  {
        path: "/content/sumit/us/en/magazine",
        excerpt: "",
        name: "magazine",
        title: "Magazine",
        lastModified: "2022-05-23 15:47:00",
        created: "2022-03-06 21:36:18"
      }
	  We can alter this behavior to show properties according to our requirement.
14.6 p.hits = full -> will give full property name. Eg: above result will look like->
{
"jcr:path": "/content/sumit/us/en/magazine",
"jcr:primaryType": "cq:Page",
"jcr:createdBy": "admin",
"jcr:created": "Sun Mar 06 2022 21:36:18 GMT+0530"
}

14.7 To get selective properties:
p.hits = selective
p.properties = jcr:content/sling:resourceType

	 eg: above will become as
	 {
      "jcr:content": {
           "sling:resourceType": "sumit/components/page"
                    }
     }

14.8 To search property tags, we can make use of predicate called tagid.
type = cq:Page
path = /content/sumit
tagid.property = jcr:content/cq:tags
tagid = we-retail:activity/swimming

14.9 We make use of predicate daterange to search for dates

      type = cq:Page
      path = /content/sumit
      daterange.property = jcr:content/cq:lastModified
      daterange.lowerBound = 2021-12-11
      daterange.upperBound = 2022-12-11 

14.10 If a query result exceeds more eg: 200+ ,
p.limit = 20 -> will always give 20 result
We can combine p.limit with another predicate called p.offset to fetch next values eg:
p.offset = (n-1) -> we pass value of n from frontend i.e 1,2 etc and based on that we see next results.
p.limit = 20

14.11 Whenever we search, then query always return all result and then based on p.limit it shows the data.
However we can restrict the behavior to searh all data and instruct to search top 10 or 20 etc results using another
predicate called p.guessTotal

       type = dam:Asset
       path = /content/dam/we-retail
       p.offset = 0
       p.limit = 20
       p.guessTotal = true -> will search only 20 results

Note - we can also assign p.guessTotal = 100, to search in top 100 results only.



15. ----Composite Components in AEM using "cq:template"------

15.1  Create a node (nt:unstructured) called cq:template under your component.
15.2  Create another node below cq:template, eg: container and sling:resourceType -> foundation/components/parsys
15.3  Create node that you want to be dropped by default eg : text and point it to respective resourceType.
15.4  Make sure the sightly of your component includes ->
<div data-sly-resource="${'container' @ resourceType='foundation/components/parsys'}" data-sly-unwrap></div>
Here name "container" should match with the node name created under cq:template node.

16. ----Event Handling---------

AEM provides 4 ways to handle events using below API's:

16.1 JCR API's ->  implement EventListener. Event handling done at JCR/Repository level and registered via ObservationManager (refer JCRHandler.java)

16.2 OSGi API's ->  implement EventHandler (refer OSGiEventHandler.java)
a. are registered with Framework service registry(i. like OSGi service) and are notified with an Event Object.
b. can inspect the received event object to determine its topic and properties.
c. event objects must be registered with a service property EventConstants.EVENT_TOPIC whose values are list of topics
for which the event handler listen to. 	  
d. can also be registered with EventConstants.EVENT_FILTER service property to further filte the events.


16.3 Sling API's -> implement ResourceChangeListener
a. are registered with Framework service registry(i. like OSGi service) and are notified with an ResourceChange Object.
b. can inspect the received ResourceChange objects to determine the type of change,location and other properties.
c. ResourceChange must be registered with a service property PATHS whose value is list of resource paths .
d. must be registered with service property CHANGES whose value is list of event type.

16.4 Sling Job Manager/Consumer -> JobManager and JobConsumer
a. gives guruantee of processing atleast once.
b. JobManager allows to create new jobs , search for jobs, and get staistics about the current state.
c. JobConsumer consumes a Job. It registers with the PROPERTY_TOPICS service registry property.
d. Steps to create ->
1. Create a Event Handler(using Sling/OSGi API) -> GeeksJobCreater.java
2. Inside the Handler, we will create a Custom Job using Job Manager i.e Job job=jobManager.addJob("geeks/job",jobProperties);
3. We create JobConsumer Service to consume this Job.(GeeksJobConsumer.java) This Job consumer returns one of 4 status name:
OK -> Process Finished Successfully
FAILED -> Proceesing Failed but might be retried(depending on the limit of retries set)
CANCEL -> Processing failed permanently and will not be retried.
ASYNC -> Processin Will Be Done Asynchronously.

NOTE - 1. If a Job is failed , then AEM stores the data under /var/eventing/jobs/cancelled with all relevant information.
2. AEM Sling Event Console http://localhost:4502/system/console/slingevent contains info like queue,retries etc.
3. We can edit retries , delay etc in config called - Apache Sling Job Default Queue



17.    ---Context Aware Configuration---

17.1 UseCase -> If we want the different configuration for different page hierarchy eg: us,in,de then we can make use of factory OSGi
configuration but if we want different config for subnodes as well under them eg: under us -> en and es and so on...In such cases
factory config will become complex to manage.
To overcome this, we make use of Context Aware Configuration.
17.2 CAC are stored under /conf/<project>. eg: /conf/sumit/us , /conf/sumit/in
17.3 We add property sling:configRef to page hierarchy in /content. The value of this property is equal to specific config that we want to load from /conf.
17.4 Context Aware configuration resolve from child to parent. For example
/conf/aemgeeks/us/en/sling:configs/GeeksCAConfig
/conf/aemgeeks/us/sling:configs/GeeksCAConfig
/conf/aemgeeks/sling:configs/GeeksCAConfig
If CA Config does not exist in /conf/aemgeeks/us/en/ then it will look at parent /conf/aemgeeks/us/
it keep looking to parents until it find configuration. Keep in mind, you have added sling:configRef property in content.
17.5 Steps to create context aware.
a. Create OSGI Config and Sling Model consuming the config -> CAConfigImpl.java
b. Under /conf/sumit/sling:configs/ create a node nt:unstructured -> com.aem.sumit.core.services.contextaware.GeeksCAConfig. Add the properties
that were defined in OSGI Service.
c. Under /content/sumit/jcr:content -> Add a property called sling:configRef -> assign the values as /conf/sumit. This will look for sling:configs
in /conf/sumit and assign the value to our component.

18.   --------Workflow----------

18.1 To ceate Workflow,Workflow Model needs to be created which contains series of steps.
Worflow model is stored in 2 places:
1. /conf/global/settings/workflow/models/geeks-model -> Model Design
2. /var/workflow/models/geeks-model -> Runtime Model (used when workflow is executing)

18.2 Different Steps:
1. Container Step -> When you want to execute another workflow(child workflow)
2. Participant Step -> When you want human interaction i.e assign some task.
3. Decision Step -> When you need branching (AND / OR)
4. Process Step -> Own custom process.

18.3 Common ways to execute workflow:
1.Launcher -> When you want to automate the trigger of the workflow eg: perform some task when page is created.
2. API/Code
3. Manually.



18.4 Launcher can be created with console - http://localhost:4502/libs/cq/workflow/admin/console/content/launchers.html.
If we have access to WorkflowModel and Payload , then we can trigger Workflow from backend (SlingModel,Servlet,Scheduler,Services)
Below are the steps to trigger:
1. Get WorkflowSession ->          WorkFlowSession session = resourceResolver.adaptTo(WorkflowSession.class)
2. Get WorkflowModel   ->          WorkflowModel model  = session.getModel("/var/...")
3. Get WorkFlowData(i.e payload)   WorkflowData data = session.newWorkflowData("JCR_PATH",payload);
4. Start workflow      ->          session.startWorkflow(model , data)

	  We can also pass metadata.         Map<String,Object> metaData = new HashMap();
	                                     session.startWorkflow(model , data, metaData);
										 
		http://localhost:4502/libs/cq/workflow/admin/console/content/models.html -> 
		Page Version For All Sumit Pages 
		1. This workflow creates a page version and is triggered via launcher which listens for cq:Page modified under /content/sumit/
        2. Launcher can be searched with globbing pattern /content/sumit/us	in Launchers Console.
        Page Version
        1. This workflow makes use of custom workflow process created below(i.e GeeksWorkflowProcess.java)	
        2. This workflow is triggered from servlet - SimpleWorkflow.java.
           http://localhost:4502/bin/executeworkflow?page=/content/sumit/us/en/adventures
           When we hit the servlet, the version will be created for page passed as query parameter and two properties(approver1 and approver2) will be added in jcr:content .		   

18.5 Custom Workflow Process - GeeksWorkflowProcess.java (This custom process step will add two properties(approver1 and approver2) in the page that is passed as Payload and create a version)
1. This will be availabele inside dropdown of Process Step.
2. implements WorkflowProcess and overrides execute(workitem,workflowsession,metadatamap)
3. property -> process.label is important as it will be selected in process step.
4. We can get dialog properties by MetaDataMap using key ("PROCESS_ARGS")

	  NOTE -> Custom Workflow Process and Custom Workflow Step are two different things.
	          Custom Workflow Process -> is a backend module which can be selected when we use Process Step .
			                             It doesn't have its dialog and have to use dialog of Process Step.
              Custom Workflow Step -> To write custom workflow step, we need a component. This component will not have rendering script and backend logic.
                                      It will have dialog and editConfig node. When we link Custom Workflow Process (eg:GeeksWorkflowProcess ) to this
            						  component,then this setup is referred as Custom Workflow Step.
                                      This will be now available as a new step in workflow model, which we can drag and drop.									  

18.6 Complete flow for creating Custom Workflow Step.
1. Created custom workflow process -> GeeksWorkflowStep.java
2. Created component -> geeksworkflowstep. It has cq:dialog and cq:editConfig.
Under cq:editConfig -> cq:formParameters , important property is PROCESS whose value is path of our
Workflow Process created in Step1.
i.e  PROCESS="com.aem.sumit.core.workflow.GeeksWorkflowStep"
3. For the first time , this new Workflow Step will not be available to drag and drop in Workflow Model.
We need to copy the 'default' node from /libs/settings/wcm/designs/default	  to /apps/settings/wcm/designs/default
4. Under /apps/settings/wcm/designs/default/jcr:content/model/flow , we added our component group .
5. This new Custom Workflow Step can now be added by drag and drop and values can be configured in dialog.

     NOTE - Whatever property that we define in cq:dialog, the name should be prepended with /metaData
            eg: name -> ./metaData/BRAND	 


18.7 Refer Workflow Model - Geek Participant Step -> to understand Partipant Step.

18.8 Workflow Stages -> Whenever an approve approves/rejects a task assigned , he is not aware of the history of the workflow i.e how far it has reached.
With the help of workflow stages, we divide the entire workflow into logical sections and each section contains the info.
Refer workflow-stage.png for pictorial view.
1. To create stages/sections, edit the model and select Stages Tab and add the stages you want(eg: Create, Review, Approve)
2. Open the Workflow Step, and you will find the new drop-down where you can assign the stage.
3. Once stages are assigned. Approver can go to inbox and click on open -> Workflow Info .

18.9 Refer Worflow Model - Geek Publish Workflow -> approve/reject the page activation.	  

--------------------------------------------Dispatcher Setup----------------------------------------------
1. Download httpd-2.2.2-win32-x86-no_ssl (apache web server) and dispatcher module dispatcher-apache2.2-windows-x86

2. Double click ssl file and install and change the directory to C:\apache2\

3. Copy disp_apache2.2.dll to C:\apache2\modules.

4. In apache 2.2, starting point is httpd.conf located at C:\apache2\conf.

5. Open httpd.conf and add the module we added:
   LoadModule dispatcher_module modules/disp_apache2.2.dll

6. Copy IF block from httpd.conf.disp2 and paste in httpd.conf  . httpd.conf.disp2 file is located in dispathcer-apache2.2
   <IfModule disp_apache2.c> ....</IfModule disp_apache2.c>

7. The IF module tells about the configuration files of dispatcher.

8. Again copy the <Directory /> block to httpd.conf

9.The <Directory /> block has "SetHandler dispatcher-handler" which tells that all processing will be done by
our module that we loaded i.e disp_apache2.2.dll

10. Copy dipatcher.any to C:\apache2\conf
    Note: In httpd.conf, ensure that "ServerRoot "C:/apache2" always points to apache installation directory.

11. Restart the apache server. If everything is fine server will restart and log with name "dispatcher.log" will be
    generated at C:\apache2\logs

12. Change the cache root path in dispatcher.any under /cache farm.
    /docroot "C:/apache2/htdocs"


##AEM + SOLR Integration
a)...Below are the use cases for different scenarios.
 1. Servlet - Index,Delete and Search Page Data to Solr.
 2. Scheduler - Index AEM Page to Solr at scheduled interval for different sites.
 3. ReplicationEventListener - Index AEM Page on publish and Un-Publish.

 Classes Included are:
 1. SolrSearchServlet - Servlet.
 2. SolrSearchHelper
 3. PageService - Service to get Page Data for Index.
 4. SolrServiceAPI - Make Rest Call to Solr Server.
 5. SolrOSGIConfig - Factory OSGI Config.
 6. SolrOSGIConfigService - Service to get OSGI Configuration.
 7. SolrServiceManager - Get OSGI Config for specific site.
 8. SolrOSGISchedulerConfig - OSGI config for Scheduler
 9. SolrOSGISchedulerConfigService - Get OSGI Configuration for Scheduler.
 10. SolrCAConfig - Context Aware Configuration.

 HighLevel Flow:
 1. Servlet/Scheduler/EventListener -> will create object of SolrSearchHelper.
 2. SolrSearchHelper -> Gets the current siteID based on the context(CA) using SolrCAConfig.
 3. Using the siteID, SolrSearchHelper gets the corresponding solrEndpoint using SolrServiceManager.
 4. SolrSearchHelper makes the REST call to Solr.

 HighLevel Flow For Servlet:
 1. SolrSearchServlet -> calls services PageService,SolrServiceManager,SolrServiceAPI
 2. SolrSearchServlet -> Create SolrSearchHelper object with passing SolrServiceManager inside constructor.

b) Install Solr - Unzip the file .
1. Run solr start in /bin
2. Access http://localhost:8983
3. Add core:
   name: techproducts
   instanceDir: D:\world\software\solr-8.11.2\solr-8.11.2\server\solr\configsets\sample_techproducts_configs
4. Copy the sample data to this core:
   Go to D:\world\software\solr-8.11.2\solr-8.11.2\example\exampledocs
   Run the command -> java -jar -Dc=techproducts post.jar *.xml
5. Create two cores:
    a). Create folders weretail and wknd under D:\world\software\solr-8.11.2\solr-8.11.2\server\solr
    b). Copy data and conf folder folders from D:\world\software\solr-8.11.2\solr-8.11.2\server\solr\configsets\sample_techproducts_configs
        and paste under weretail and wknd folders.
    c)  Empty the data folder for both wknd and weretail.
    d) From solr admin, click add core and enter name and dir as weretail.
       Repeat same for wknd.
6. Confugure OSGI service and CA,hit the servlet - http://localhost:4502/bin/solrsearch?searchParameter=index&sitePath=/content/sumit/us/en
7. 

## AEM Scratch
-------------------------------------------AEM FROM SCRATCH_______________

1. Create a project using maven archetype(version was 35) and deploy the package using $ mvn clean install -PautoInstallSinglePackage.
   mvn org.apache.maven.plugins:maven-archetype-plugin:2.4:generate -DarchetypeGroupId=com.adobe.granite.archetypes -DarchetypeArtifactId=aem-project-archetype -DarchetypeVersion=15 -DarchetypeCatalog=https://repo.adobe.com/nexus/content/groups/public/

2. This will deploy 4 packages- ***apps,***config,***content,***all.

3. The generated AEM project is made up of individual Maven modules, each with a different role.
   core - Java Code, primarily back-end developers.
   frontend - Contains source code for CSS, JavaScript, Sass, Type Script, primarily for front-end developers.
   apps - Contains component and dialog definitions, embeds compiled CSS and JavaScript as client libraries.
   content - contains structural content and configurations like editable templates, metadata schemas (/content,%20/conf?lang=en).
   all - this is an empty Maven module that combines the above modules into a single package that can be deployed to an AEM environment.

4. The core module contains all of the Java code associated with the project. When built it deploys an OSGi bundle to AEM. To build just this module:
   $ mvn clean install -PautoInstallBundle
   Similarly apps and content modules can be deployed:
   $ mvn clean install -PautoInstallPackage

5. Create Editable template. Archetype creates empty page template. We can leverage it and create our template.  
   Editable template has three main areas: structure,initialcontent,policies.
   Create a page using newly created template and use XF as header and footer.

6. Client-Side Libraries provides a mechanism to organize and manage CSS and JavaScript files necessary for an AEM Sites implementation.
   The basic goals for client-side libraries or clientlibs are:
   Store CSS/JS in small discrete files for easier development and maintenance.
   Manage dependencies on 3rd party frameworks in an organized fashion.
   Minimize the number of client-side requests by concatenating CSS/JS into one or two requests.

   Note- css and js can be minified using Adobe Granite HTML Library Manager.

7. Client-side libraries have some limitations when it comes to support of languages like Sass or TypeScript.
   There are a number of open-source tools like NPM and webpack that accelerate and optimize front-end development.
   The goal of the ui.frontend module is to be able to use these tools to manage a majority of front-end source files.

8. main.scss under src/main/webpack/site is the entry point for all SASS files in frontend module.

9. main.ts is the entry point used by webpack. main.ts includes main.scss and a regular expression to collect any .js or .ts files in the project.

10.Inspect the files beneath src/main/webpack/site/styles:
These files styles for global elements in the template, like the Header, Footer and main content container. The CSS rules in these files target different HTML elements header, main, and footer.

11. The important clientlibs folder generated by archetype are
    clientlib-base - Base level of CSS and JavaScript needed for WKND Site to function(embeds Core Component client libs)
    clientlib-site - Contains site-specific theme for the WKND Site (Generated by the ui.frontend module)
    clientlib-dependencies - Embeds any 3rd party dependencies  (Generated by the ui.frontend module)

In our page component we'll include clientlib-base as it contains core component clientlibs.
In customheaderlibs.html, we will use css i.e
<sly data-sly-use.clientLib="/libs/granite/sightly/templates/clientlib.html">
<sly data-sly-call="${clientlib.css @ categories='sumit.base'}">
</sly>
In customfooterlibs.html, we will use JS i.e
<sly data-sly-use.clientlib="/libs/granite/sightly/templates/clientlib.html">
<sly data-sly-call="${clientlib.js @ categories='sumit.base'}"/>
</sly>

And remaining clientlib-site and clientlib-dependencies will be generated by frontend module and inserted into clientlibs inside /apps.
To include this, go to editable template structure(eg: ArticlePage Template) -> Click on Page Information -> Page Policy -> Properties Tab
and add both clientlibs.
***Note***
A key integration piece built into the ui.frontend module, aem-clientlib-generator takes the compiled CSS
and JS artifacts from a webpack/npm project and transforms them into AEM client-side libraries.

How it works?
1. Run npm install in frontend module.
2. Start the webpack dev server in watch mode by running the following command:$ npm run watch.
   This will compile the src files in the ui.frontend module and sync the changes with AEM at http://localhost:4502
   The command npm run watch ultimately populates the clientlib-site and clientlib-dependencies in the ui.apps module
   which is then synced automatically with AEM.

3. This will create site.css and site.js in frontend/dist/clientlib-site/
   These file will be copied to apps/src/main/content/jcr_root/apps/wknd/clientlibs/clientlib-site

4. Inspect the file ui.frontend/clientlib.config.js. This is the configuration file for an npm plugin,
   aem-clientlib-generator that transforms the contents of /dist into a client library and moves it to the ui.apps module.


12. The Style System allows developers and template editors to create multiple visual variations of a component.
    The general idea with the Style System is that authors can choose various styles of how a component should look.
    The “styles” are backed by additional CSS classes that are injected into the outer div of a component.
    In the client libraries CSS rules are added based on these style classes so that the component changes appearance.

13. To create a Underline Style for Title component:
    1. Go to Structure of ArticlePage Template and click on Policy Icon of Title Component.
    2. Go to Style Tab and add Styles eg: Underline(i.e name that will appear when we click on paint brush) cmp-title--underline(i.e css class).
    3. Add this css class in our _title.scss file in frontend module and npm run watch, this will insert the css in /apps .
    4. Go to Title Component and click on paint brush and select "Underline" to apply this style in Title Component.

14. Nodes created during custom component creation.
    1. Create byline folder add a new file named .content.xml  (this XML file provides the definition for the component, including the title, description and group.)
    2. Beneath the byline folder, add a new file byline.html (it's responsible for the HTML presentation of the component.)
       Naming the file the same as the folder is important, as it becomes the default script Sling will use to render this resource type.
    3. Create the Dialog definition - Beneath the byline folder, create a new folder named _cq_dialog.
       Beneath byline/_cq_dialog add a new file named .content.xml(This is the XML definition for the dialog.)
    4. Create the Policy dialog - Beneath the byline folder, create a new folder named _cq_design_dialog.
       Beneath byline/_cq_design_dialog create a new file named .content.xml

***Note***
sling:resourceSuperType="core/wcm/components/image/v2/image"
These dialog node definitions use the Sling Resource Merger to control which dialog tabs are inherited from the sling:resourceSuperType
component, in this case the Core Components’ Image component., Sling Resource Merger is used to hide irrelevant fields that are otherwise inherited from the sling:resourceSuperType,
as seen by the node definitions with sling:hideResource="{Boolean}true" property.

15. Creating Byline Sling Model - Sling Models are annotation driven Java “POJO’s” (Plain Old Java Objects) that facilitate the mapping of data from the JCR to Java variables.
    The Byline Sling Model will rely on several Java APIs provided by AEM. These APIs are made available via the dependencies listed in the core module’s POM file.
    In pom.xml of core- For AEM as a Cloud Service Only "aem-sdk-api" is added.
    For AEM 6.5/6.4 "uber-jar" is added.

    1. Create ByLine.java interface and its implementation ByLineImpl.java.
    2. Annotate ByLineImpl with
       @Model(
       adaptables = {SlingHttpServletRequest.class},
       adapters = {Byline.class},
       resourceType = {BylineImpl.RESOURCE_TYPE},
       defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
       )
       ***The @Model annotation registers BylineImpl as a Sling Model when it is deployed to AEM.
       ***The adaptables parameter specifies that this model can be adapted by the request.
       ***The adapters parameter allows the implementation class to be registered under the Byline interface.
       This allows the HTL script to call the Sling Model via the interface (instead of the impl directly).
       ***The resourceType points to the Byline component resource type (created earlier) and helps to resolve the correct model if there are multiple implementations.
       3.Since Sling Models are Java POJO’s, and not OSGi Services, the usual OSGi injection annotations @Reference cannot be used, instead Sling Models provide a special @OSGiService annotation that provides similar functionality.
    4. @PostConstruct is incredibly useful and acts in a similar capacity as a constructor, however, it is invoked after the class is instantiated and all annotated Java fields are injected.
       Whereas other Sling Model annotations annotate Java class fields (variables),
       @PostConstruct annotates a void, zero parameter method, typically named init() (but can be named anything).

16. In byline.html we created in the earlier set up of the AEM Component:
     <div data-sly-use.placeholderTemplate="core/wcm/components/commons/v1/templates.html">
     </div>
     <sly data-sly-call="${placeholderTemplate.placeholder @ isEmpty=false}"></sly>
    1. The placeholderTemplate points to Core Components’ placeholder, which displays when the component is not fully configured.
     This renders in AEM Sites Page Editor as a box with the component title, as defined above in the cq:Component’s jcr:title property.
    2. The data-sly-call="${placeholderTemplate.placeholder @ isEmpty=false} loads the placeholderTemplate defined above and passes in a boolean value (currently hard-coded to false) into the placeholder template.
    	When isEmpty is true, the placeholder template renders the grey box, else it renders nothing. 

    <!--/* byline.html */-->
   <div data-sly-use.byline="com.adobe.aem.guides.wknd.core.models.Byline"
    data-sly-use.placeholderTemplate="core/wcm/components/commons/v1/templates.html"
    data-sly-test.hasContent="${!byline.empty}"
    class="cmp-byline">
    <div class="cmp-byline__image"
        data-sly-resource="${ '.' @ resourceType = 'core/wcm/components/image/v2/image' }"> /* This data-sly-resource, included the current resource via the relative path '.', and forces the inclusion of the current resource (or the byline content resource) with the resource type of core/wcm/components/image/v2/image.*/
    </div>
    <h2 class="cmp-byline__name">${byline.name}</h2>
    <p class="cmp-byline__occupations">${byline.occupations @ join=', '}</p>
   </div>
   <sly data-sly-call="${placeholderTemplate.placeholder @ isEmpty=!hasContent}"></sly>
