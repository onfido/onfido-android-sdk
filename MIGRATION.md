# Onfido Android SDK Migration Guide

## `0.9.2` -> `1.0.0`

### Breaking changes

- Removed the `allowMetrics(boolean)` method from the `OnfidoConfig.Builder` object. Every call to this method should be deleted
- Removed the previously deprecated `FlowStep.MESSAGE_IDENTIFY_VERIFICATION` enum instance, as it was too specific for our generic flow intentions. 
Every previous inclusion of this object on a flow should be replaced by a custom `MessageScreenStep`
