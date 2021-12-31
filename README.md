# Health Care

Summary :
		In today's world of digitalization, every daily life task is being digitalized to increase the ease of people to access the services and facilities provided to them through automated and online platforms. In such an environment people prefer to complete all their tasks online without the hassle of physical visits to the platform. But one area of life that still lacks digitalization in our country is health care. We plan to develop an application that will allow the users to view and select the available doctors and physicians in their specified location to book their visit and to maintain the history of their visits. 
By using our application, the patients will be able to choose their physician or doctor in a specified city according to requirements of location or nearest availability, time or quickest availability, reputation or reviews etc. All this will be available to the patients without the hassle of visiting the hospitals or searching for the available doctors through various platforms. All information about the doctors and the facility of appointment booking will be provided online.
Similarly, the doctors and physicians using the application can view their appointments with the patients.

Core requirements:

	1. Architecture and Application components
		a. Data Storage
		b. Services and Broadcast Receiver
	2. Responsive UI design
	3. Web connectivity
	4. Monetization (Admob) and analytics


Functional requirements:
	1. System will allow the registration of new users both patients and doctors.
	2. System will allow patients to search doctors for any medical field.
	3. System will allow patients to filter the list of doctors based on availability in a location.
	4. System will allow patients to filter the list of doctors based on required timing.
	5. System will allow patients to book an appointment with the selected doctor on selected time.
	6. System will allow patients to view the status of appointment booking.
	7. System will allow doctor to view the details of appointments with patients.

Architectural analysis:
	The architecture requirement of data storage will be completed under the functional requirements 1 and 5. It will be required to store data of all patients, doctors and appointment records. Moreover, the services provided by the above mentioned requirements include: services to store and update user data (background service) in requirement 1 and 5, services to search and fetch search results (bound services) of available doctors in requirements 2,3 and 4 , and services entertaining the request to view the appointments (bound services) booked by patients in  requirement 7.
