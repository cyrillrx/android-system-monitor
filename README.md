# A monitoring app for Android

This is a simple application that monitors the health of your device.

It collects the following device metrics:
- System CPU load
- Global RAM usage
- Battery level

The user can configure the thresholds at which they want to be alerted for each of the above metrics.
Anytime this happens, a notification alerts the user

If the user opts so, a notification is also displayed whenever the metrics returns below the threshold.

# Note 
Android O prevents access to /proc/stat
https://issuetracker.google.com/issues/37140047

# Possible improvements
- Use a persistent solution to store alert history (e.g. Room)
- Split the all-in-one layout into 3 stat views (and use an adapter to display them)
- Use Intent.ACTION_BATTERY_CHANGED to prevent unnecessary calls to battery level
- Store actual stats values in addition to the percentage of usage
- Improve service/activity binding with more suitable data structures