package com.example.androidjetpackcompose

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidjetpackcompose.ui.theme.AndroidJetpackComposeTheme

// I'm new to Jetpack Compose and want to learn it properly. Please add detailed and beginner-friendly comments to my code, explaining what each part does in simple language. Also, feel free to improve the code if there are better practices I should follow.
/**
 * Main activity class for our app.
 * In Android, an Activity is like a single screen in your app.
 *
 * ComponentActivity is the base class for activities using Jetpack Compose.
 * It provides the integration between Android's traditional View system and Compose.
 */
class MainActivity : ComponentActivity() {
    /**
     * The onCreate method is called when the activity is first created.
     * This is where we set up our UI using Jetpack Compose.
     *
     * @param savedInstanceState Bundle containing the activity's previously saved state.
     * This parameter is null when the activity is first created, but will contain data if:
     * 1. The activity is being recreated after being destroyed (e.g., during configuration changes like rotation)
     * 2. The system killed the activity to reclaim resources and user navigates back to it
     * 3. When onSaveInstanceState() was called to save data before destruction
     *
     * Use this to restore UI state like scroll positions, form data, or any transient state
     * that should persist across activity recreation.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Make content draw behind system bars (like status bar) for a more immersive UI
        enableEdgeToEdge()

        // setContent defines the UI of our activity using Compose
        setContent {
            // Apply our app's theme to ensure consistent styling
            AndroidJetpackComposeTheme {
                // Scaffold provides a framework for implementing the basic Material Design layout
                // It can include top bars, bottom bars, floating action buttons, etc.
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    // innerPadding helps us avoid overlapping with system UI elements

                    // Display our business card component
                    // In Compose, UIs are built by composing smaller components together
                    CreateBizCard(
                        context = this@MainActivity,
                        name = "Android Developer",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

/**
 * A Composable function that creates a business card UI component.
 *
 * @param name Text to display inside the card
 * @param modifier Optional modifier that will be applied to the outermost element
 */
@Composable
fun CreateBizCard(
    context: Context,
    name: String,
    modifier: Modifier = Modifier
) {

    val buttonClickedState = remember {
        mutableStateOf(false)
    }

    // Surface is a container that provides a consistent background and styling
    Surface(
        modifier = modifier
            .fillMaxWidth()  // Makes the Surface use all available width
            .fillMaxHeight() // Makes the Surface use all available height
    ) {
        Card(
            // Modifier chain controls the Card's dimensions and spacing
            modifier = Modifier
                .width(200.dp)  // Fixed width of 200dp
                .height(280.dp) // Fixed height of 280dp
                .padding(12.dp), // Space around the Card's edges

            // Gives the Card rounded corners with 15dp radius
            shape = RoundedCornerShape(corner = CornerSize(15.dp)),

            // Sets the Card's background color
            colors = CardDefaults.cardColors(containerColor = Color.White),

            // Adds a shadow under the Card to create depth
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            // Column arranges its children vertically from top to bottom
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 12.dp),

                // Controls how items are spaced within the Column
                verticalArrangement = Arrangement.Top,

                // Centers items horizontally within the Column
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Adds a circular profile picture
                CreateProfileImage()

                // Adds a horizontal divider line between profile image and text
                HorizontalDivider(
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 4.dp, bottom = 4.dp),
                    thickness = 1.dp,
                    color = Color.LightGray
                )

                CreateInfoText(name)

                // Button to toggle the portfolio content visibility
                Button(
                    // Add some top padding to separate the button from elements above it
                    modifier = Modifier.padding(top = 16.dp),
                    onClick = {
                        // This is executed when the button is clicked
                        // Show a brief message to the user using Android's Toast
                        Toast.makeText(context, "Portfolio button clicked", Toast.LENGTH_SHORT).show()

                        // Toggle the state variable between true and false
                        // This is how we manage UI states in Compose - when this value changes,
                        // Compose will automatically recompose (redraw) the affected UI parts
                        buttonClickedState.value = !buttonClickedState.value
                    }
                ) {
                    // The button's label/content - in this case just text
                    Text(
                        text = "Portfolio",
                        // Using predefined text style from MaterialTheme ensures consistent typography
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                
                // Conditional UI based on the button state
                // This is a key concept in Compose: declarative UI that responds to state changes
                if (buttonClickedState.value) {
                    // When buttonClickedState is true, show the portfolio content
                    Content()
                } else {
                    // When buttonClickedState is false, show an empty box (nothing)
                    // Using an empty Box is a common pattern for "showing nothing" in Compose
                    // while maintaining the structure of your composables
                    Box {}
                }
            }
        }
    }
}

/**
 * A Composable function that displays the user's information in a vertical layout.
 * 
 * @param name The name to display in the greeting text
 */
@Composable
private fun CreateInfoText(name: String) {
    // Column arranges its children vertically one below another
    // Think of it like stacking blocks from top to bottom
    Column{
        // First Text component - The main greeting with the user's name
        Text(
            text = "Hello $name!",
            // Using predefined text styles from MaterialTheme ensures consistent typography across app
            style = MaterialTheme.typography.headlineSmall,
            // Using colors from the theme allows automatic support for light/dark modes
            color = MaterialTheme.colorScheme.primary,
            // Limits text to a single line, preventing wrapping to multiple lines
            maxLines = 1,
            // If text is too long, this determines how it's cut off (with ellipsis "...")
            overflow = TextOverflow.Ellipsis
        )

        // Second Text component - The subtitle text
        Text(
            text = "Learning Android Jetpack Compose is fun!",
            // titleSmall is smaller than headlineSmall, creating visual hierarchy
            style = MaterialTheme.typography.titleSmall,
            // Using secondary color creates visual distinction from the main heading
            color = MaterialTheme.colorScheme.secondary
        )

        // Third Text component - The username/handle
        Text(
            text = "@CodingWithDevesh",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.secondary,
            // Adding modifier here would let us control specific properties of this text component
            // For example: modifier = Modifier.padding(top = 4.dp) would add space above this text
        )
    }
}

/**
 * A Composable function that creates a visually appealing circular profile image with a border.
 * Private function since it's only used within this file, keeping the code organized and modular.
 *
 * Breaking UI into smaller functions like this makes the code easier to read, test, and maintain.
 */
@Composable
private fun CreateProfileImage(modifier: Modifier = Modifier) {
    Surface(
        // Modifier defines how this composable looks and behaves in terms of size, spacing, etc.
        modifier = modifier
            .size(150.dp)  // Sets the size of the circular surface to 150dp by 150dp
            .padding(5.dp),  // Adds spacing around the surface to separate it from other UI components

        // CircleShape makes the Surface a perfect circle. It's often used for profile pictures.
        shape = CircleShape,

        // Shadow elevation adds a soft shadow beneath the Surface, giving it a floating effect.
        shadowElevation = 5.dp,

        // Border gives the circular frame a thin outline with a dark gray color for better visibility.
        border = BorderStroke(0.5.dp, Color.DarkGray)
    ) {
        Image(
            // PainterResource loads the image asset from the app's drawable resources. 
            // Ensure your image resource is in the `res/drawable` directory with the correct name.
            painter = painterResource(id = R.drawable.profile_image),


            // Modifier applies additional styling to the image, such as internal padding.
            modifier = Modifier.padding(2.dp),  // Adds a small internal padding to avoid tight edges

            // ContentScale ensures the image fills the circular frame while maintaining its proportions.
            // Using ContentScale.Crop will crop the image if it doesn't match the aspect ratio.
            contentScale = ContentScale.Crop,

            // This is a description of the image for accessibility tools such as screen readers.
            // Always provide concise and helpful content descriptions for better app accessibility.
            contentDescription = "A circular profile image",

            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )
    }
}

/**
 * Content is a composable function that displays a portfolio section.
 * 
 * @Preview annotation allows us to see this component in Android Studio without running the app.
 * - showBackground: Shows a background in the preview
 * - name: Gives this preview a specific name in the Preview panel
 */
@Preview(showBackground = true, name = "Portfolio Content")
@Composable
fun Content() {
    // Box is a layout container that stacks its children on top of each other
    // Think of it as a frame where we can position content relative to its edges
    Box(
        modifier = Modifier
            .fillMaxHeight()  // Use all available vertical space
            .fillMaxWidth()   // Use all available horizontal space
            .padding(5.dp)    // Add 5dp space around all edges
    ) {
        // Surface is a container with consistent background, elevation and shape
        // It's perfect for creating card-like UI elements
        Surface(
            modifier = Modifier
                .fillMaxHeight()  // Use all available vertical space within the Box
                .fillMaxWidth()    // Use all available horizontal space within the Box
                .padding(3.dp),    // Add 3dp of padding inside the Box but outside the Surface
            
            // Add a light gray border around the Surface
            border = BorderStroke(width = 1.dp, color = Color.LightGray),
            
            // Apply rounded corners to the Surface (6dp radius)
            shape = RoundedCornerShape(corner = CornerSize(6.dp))
        ) {
            // Inside our Surface, we're displaying the Portfolio component
            // We're passing a list of sample project names that will be shown
            Portfolio(
                data = listOf("Project 1", "Project 2", "Project 3", "Project 4", "Project 5")
            )
            
            // Note: In a real app, you might want to fetch this data from a repository
            // or pass it in as a parameter rather than hardcoding it here
        }
    }
}

/**
 * Portfolio is a composable function that displays a scrollable list of projects.
 * 
 * @param data A list of project names to display in the portfolio
 */
@Composable
fun Portfolio(data: List<String>) {
    // LazyColumn is like RecyclerView in traditional Android
    // It only renders items that are visible on screen, making it memory efficient
    // for long lists
    LazyColumn {
        // The items() function takes a list and creates a composable for each item
        // dataItem is each individual string from our list
        items(data) { dataItem ->
            // Card creates a material design card with built-in elevation and shape
            Card(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 13.dp) // Better padding separation
                    .fillMaxWidth(), // Make the card use all available width
                // RoundedCornerShape is more common in Material Design than RectangleShape
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                // Row arranges its children horizontally (side by side)
                Row(
                    modifier = Modifier
                        .fillMaxWidth() // Use full width of the card
                        .padding(12.dp) // Single padding instead of two consecutive ones
                        // No need for background color as Card already provides one
                ) {
                    // This creates a smaller version of our profile image
                    CreateProfileImage(modifier = Modifier.size(70.dp))
                    
                    // Column arranges its children vertically (top to bottom)
                    Column(
                        modifier = Modifier
                            .padding(start = 12.dp) // Add space between image and text
                            .align(alignment = Alignment.CenterVertically) // Center text vertically
                    ) {
                        // Display the project name in bold
                        Text(
                            text = dataItem, 
                            fontWeight = Bold,
                            style = MaterialTheme.typography.titleMedium // Use Material Typography
                        )
                        
                        // Add some space between the texts
                        androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(4.dp))
                        
                        // Display project description in a smaller font
                        Text(
                            text = "A great project", 
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant // Better color contrast
                        )
                    }
                }
            }
        }
    }
}

/**
 * Preview functions let Android Studio show a live preview of your UI without running the app.
 *
 * The @Preview annotation has various parameters you can customize:
 * - showBackground: When true, shows the default background color
 * - showSystemUi: Set to true to preview with system UI elements (status bar, navigation bar)
 * - uiMode: Can be used to preview in light/dark modes
 * - device: Lets you specify which device to preview on ("pixel_4", "pixel_6", etc.)
 */
@Preview(showBackground = true, name = "Business Card Preview") // Adding a name helps when you have multiple previews
@Composable
fun GreetingPreview() {
    // AndroidJetpackComposeTheme applies your app's theme (colors, typography, shapes)
    // Always wrap your previews in your theme for consistent appearance
    AndroidJetpackComposeTheme {
        // Previews the business card with sample text
        // This is exactly what will appear when you run the app
        // For preview, we can't use a real Activity context
        // One approach is to modify the CreateBizCard function to make context optional
        // or use a preview-specific version that doesn't need a context
        CreateBizCard(
            // For previews only, pass null or mock context
            context = LocalContext.current,
            name = "Android Only Preview"
        )
    }
}