package org.javarosa.core.model.actions;

import org.javarosa.core.test.Scenario;
import org.javarosa.core.util.externalizable.DeserializationException;
import org.javarosa.xform.parse.XFormParseException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MultipleEventsTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void nestedFirstLoadEvent_setsValue() {
        Scenario scenario = Scenario.init("multiple-events.xml");

        assertThat(scenario.answerOf("/data/nested-first-load").getDisplayText(), is("cheese"));
    }

    @Test
    public void nestedFirstLoadEventInGroup_setsValue() {
        Scenario scenario = Scenario.init("multiple-events.xml");

        assertThat(scenario.answerOf("/data/my-group/nested-first-load-in-group").getDisplayText(), is("more cheese"));
    }

    @Test
    public void serializedAndDeserializedNestedFirstLoadEvent_setsValue() throws IOException, DeserializationException {
        Scenario scenario = Scenario.init("multiple-events.xml");

        Scenario deserializedScenario = scenario.serializeAndDeserializeForm();
        deserializedScenario.newInstance();
        assertThat(deserializedScenario.answerOf("/data/nested-first-load").getDisplayText(), is("cheese"));
    }

    @Test
    public void serializedAndDeserializedNestedFirstLoadEventInGroup_setsValue() throws IOException, DeserializationException {
        Scenario scenario = Scenario.init("multiple-events.xml");

        Scenario deserializedScenario = scenario.serializeAndDeserializeForm();
        deserializedScenario.newInstance();
        assertThat(deserializedScenario.answerOf("/data/my-group/nested-first-load-in-group").getDisplayText(), is("more cheese"));
    }

    @Test
    public void nestedFirstLoadAndValueChangedEvents_setValue() {
        Scenario scenario = Scenario.init("multiple-events.xml");

        assertThat(scenario.answerOf("/data/my-calculated-value").getDisplayText(), is("10"));
        scenario.answer("/data/my-value", "15");
        assertThat(scenario.answerOf("/data/my-calculated-value").getDisplayText(), is("30"));
    }

    @Test
    public void serializedAndDeserializedNestedFirstLoadAndValueChangedEvents_setValue() throws IOException, DeserializationException {
        Scenario scenario = Scenario.init("multiple-events.xml");

        Scenario deserializedScenario = scenario.serializeAndDeserializeForm();
        deserializedScenario.newInstance();
        assertThat(deserializedScenario.answerOf("/data/my-calculated-value").getDisplayText(), is("10"));
        deserializedScenario.answer("/data/my-value", "15");
        assertThat(deserializedScenario.answerOf("/data/my-calculated-value").getDisplayText(), is("30"));
    }

    @Test
    public void invalidEventNames_throwException() {
        expectedException.expect(XFormParseException.class);
        expectedException.expectMessage("An action was registered for unsupported events: odk-inftance-first-load, my-fake-event");

        Scenario.init("invalid-events.xml");
    }
}
